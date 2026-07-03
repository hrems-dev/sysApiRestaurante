# Especificación de Flujos por Rol — Sistema Restaurante

> Documento generado a partir de `requerimientos.md`, `modulos.md`, `bloques.md` y `flujo-autenticacion.md`.
> Objetivo: ordenar las ideas de negocio descritas y traducirlas a cambios concretos de modelo de datos, endpoints y servicios, respetando la arquitectura por módulos ya definida (`controller / dto / entity / mapper / repository / service`).
> Este documento está pensado para ser usado como prompt de trabajo (para vos o para una IA de código) en las siguientes sesiones de desarrollo.

---

## 0. Supuestos y decisiones de diseño (confirmar antes de implementar)

Estas son las decisiones que tomé para poder cerrar los flujos. Si alguna no es correcta, se corrige el documento antes de programar.

| # | Supuesto | Justificación |
|---|----------|----------------|
| A1 | `Pedido` ya tiene (o debe tener) un campo `tipoPedido`: `MESA`, `DELIVERY`, `RECOJO` | El propio `requerimientos.md` define estados distintos para Pedido MESA vs DELIVERY, eso solo es posible si existe este campo. |
| A2 | El rol `CLIENTE` ya existe en `Rol` (lo dice la lista de entidades: ADMIN, MESERO, COCINA, REPARTIDOR, CONTABILIDAD, CLIENTE) | Por eso el cliente de delivery **no necesita una tabla nueva**: se registra como `Usuario` con `rol = CLIENTE`. |
| A3 *(corregido)* | `LugarAtencion` tiene un campo `tipoLugar: {LOCAL, DELIVERY}`. `LOCAL` agrupa las mesas físicas del salón. Existe **un único** `LugarAtencion` con `tipoLugar = DELIVERY` (sin mesas asociadas) que se usa como "lugar" de todos los pedidos delivery; la dirección específica de cada pedido **no** vive en `LugarAtencion` (sería un dato distinto por cada cliente/pedido), sino en `Pedido.direccionEntrega` | Mantiene el modelo `Pedido → LugarAtencion` siempre poblado (nunca null) y evita mezclar "tipo de atención" con "dirección puntual de un pedido". |
| A4 *(corregido)* | La selección de mesa **solo ocurre si el cliente entra por el link/QR de una mesa** (`/qr/:idMesa`). Si entra por el link público general (dominio raíz o cualquier otro acceso que no sea un QR de mesa), se trata como **cliente público**: no se le muestra selección de mesa ni carrito de "pedido en mesa", pero **sí** se le muestra, en esa misma página pública, el catálogo para delivery y el botón de **reservas** | Diferencia claramente "vine a comer ahora, ya estoy sentado" (QR) de "quiero pedir delivery o reservar para otro momento" (link público). |
| A4.1 | El cliente de mesa (QR) **no se registra**, se identifica con un código de sesión `CLI-XXXXXX` generado en frontend/backend por pedido, sin crear `Usuario` | Coincide con "cliente público, sin login" del cuadro de actores. |
| A5 | El estado "reportado" del cliente se guarda en `Usuario` (rol CLIENTE) con dos campos nuevos: `reportado: boolean`, `fechaReporte: datetime` | Evita crear una tabla nueva solo para esto. |
| A6 | El horario de atención para delivery/pedido en casa se guarda en `Configuracion` (clave-valor), no en una tabla nueva | Ya existe la entidad `Configuracion` para parámetros del sistema. |
| A7 | El "pago falso" de prueba se implementa como un modo/perfil de `PagoService`, no como una entidad nueva | Es un stub que luego se reemplaza por pasarela real (Yape/Plin/tarjeta). |
| A8 | La pantalla "sin servicio" al salir del QR de mesa es **solo lógica de frontend** (Angular), no requiere backend | Es una regla de comportamiento de sesión de prueba, no un dato persistente. |

---

## 1. Cambios en el modelo de datos

### 1.1 `Usuario` (módulo `usuario` / `autenticacion`)
Nuevos campos:
- `direccion: String` (nullable) — dirección de entrega guardada del cliente delivery.
- `referenciaUbicacion: String` (nullable) — texto libre ("frente al parque X", "colegio Y").
- `reportado: boolean` (default `false`).
- `fechaReporte: LocalDateTime` (nullable).

Regla de negocio nueva:
- Si `usuario.reportado == true`, no puede crear nuevos pedidos (validar en `PedidoService.crearPedido`).

### 1.2 `LugarAtencion` (módulo `lugar_atencion`)
Nuevo campo:
- `tipoLugar: enum {LOCAL, DELIVERY}`.

Regla nueva:
- `tipoLugar = LOCAL` → tiene `Mesa`s asociadas (salón, barra, terraza, etc.), es el flujo QR.
- `tipoLugar = DELIVERY` → registro único, sin mesas, no tiene dirección propia. Es solo el "tipo de atención" al que apunta `Pedido.lugarAtencion` cuando es un pedido a domicilio. La dirección puntual va en `Pedido` (ver 1.3).
- Sembrar (seed/DataInitializer) ese único `LugarAtencion DELIVERY` al iniciar el sistema, igual que ya se hace con el admin (`inicial.md`).

### 1.3 `Pedido` (módulo `pedido`)
Nuevos campos:
- `tipoPedido: enum {MESA, DELIVERY, RECOJO}` (si no existe aún).
- `codigoClienteMesa: String` (nullable) — el `CLI-XXXXXX` para pedidos MESA.
- `direccionEntrega: String` (nullable) — solo si `tipoPedido = DELIVERY`.
- `referenciaEntrega: String` (nullable).
- `confirmadoCliente: Boolean` (nullable) — `null` = pendiente de confirmación, `true` = cliente aceptó entrega, `false` = cliente reportó problema.
- `fechaConfirmacionCliente: LocalDateTime` (nullable).

`Pedido.lugarAtencion` **siempre** queda poblado: apunta a la mesa/salón real si es `MESA`, o al `LugarAtencion DELIVERY` único si es `DELIVERY`.

### 1.4 `Mesa` (módulo `lugar_atencion`)
Nuevo campo:
- `estadoMesa: enum {LIBRE, OCUPADA, RESERVADA}`.

Regla nueva: un job programado pasa `LIBRE → RESERVADA` cuando faltan ≤ 20 min para una `Reserva` confirmada de esa mesa, y la vuelve a `LIBRE` si la reserva se cancela/expira sin check-in.

### 1.5 `Reserva` (módulo `reserva`)
Confirmar que ya existan:
- `fecha: LocalDate`, `hora: LocalTime` (o `fechaHora: LocalDateTime`).
- `montoAdelanto: BigDecimal` (viene de `Configuracion`, no se pide manualmente al cliente).

### 1.6 `Configuracion` (módulo `configuracion`)
Nuevas claves sugeridas (clave-valor, sin tabla nueva):
- `horario.apertura` = `"11:00"`
- `horario.cierre` = `"22:00"`
- `reserva.monto_adelanto` = `"10.00"`
- `reserva.minutos_bloqueo_mesa` = `"20"`

---

## 2. Estados y su relación con colores en UI (aclarado)

Reusando los estados que ya definiste en `requerimientos.md`:

```
Pedido MESA:      PENDIENTE → EN_PREPARACION → LISTO → ENTREGADO → PAGADO
Pedido DELIVERY:  PENDIENTE → CONFIRMADO → EN_PREPARACION → LISTO → EN_CAMINO → ENTREGADO → PAGADO
```

Mapeo de colores para **panel Mesero**:
| Estado Pedido | Color | Significado visual |
|---|---|---|
| `PENDIENTE` / `EN_PREPARACION` | 🔴 rojo | "en espera" (aún en cocina) |
| `LISTO` | 🟠 naranja | "sirviendo" (cocina lo terminó, el mesero debe llevarlo) |
| `ENTREGADO` | 🟢 verde | "comiendo" (ya está en la mesa) |

Botón **"Servido"** del mesero = transición `LISTO → ENTREGADO`. Al presionarlo, el pedido desaparece del listado activo y pasa al historial pequeño de la derecha (filtrar en frontend por `estado = ENTREGADO`, orden descendente por fecha, mostrando solo los últimos N).

Panel **Cocina**: muestra pedidos en `PENDIENTE` / `EN_PREPARACION`. Botón (ej. **"Plato listo"**) = transición `EN_PREPARACION → LISTO`. No hay clasificación por color de estado (todo es "por hacer"), pero sí color por mesa/lugar (borde o fondo distinto según `pedido.lugarAtencion.id` o `pedido.mesa.id`, hash → color fijo). Los productos se muestran más grandes (cambio de layout, no de datos).

Panel **Delivery/Repartidor**: pedidos en `LISTO` (listos para recoger) → botón **"Recoger"** = `LISTO → EN_CAMINO` → botón **"Entregado"** = `EN_CAMINO → ENTREGADO` (pero el pedido **sigue visible** hasta que `confirmadoCliente != null`).

---

## 3. Flujo 1 — Cliente en mesa (QR), modo de prueba

**Importante — dos entradas distintas a la misma app pública:**
- `/qr/:idMesa` (o `/mesa/:idMesa`) → **flujo QR**: auto-selección de mesa, botón cancelar/salir, overlay "sin servicio". Este es el único caso en que se muestra selección de mesa.
- `/` (link público general, sin `idMesa`) → **flujo público**: nunca se auto-selecciona ni se muestra una mesa; el cliente ve el catálogo para delivery y el botón de reservas (ver secciones 6 y 9). Ambas rutas comparten componentes de catálogo/carrito, pero el router decide si activa el "modo mesa" o no según si vino un `idMesa` en la URL.

**Backend:** sin cambios funcionales; solo se usa el endpoint público de menú y de creación de pedido MESA.

**Frontend (Angular), lógica nueva (solo aplica a `/qr/:idMesa`):**
1. Al entrar por el link de la mesa, seleccionar automáticamente la mesa en el estado de la app (guard/resolver), sin pantalla de selección manual.
2. Generar (o pedir al backend) el `codigoClienteMesa` tipo `CLI-3F9A2C` y guardarlo en memoria/sessionStorage.
3. Botón "Cancelar y salir":
   - Limpia el estado de sesión (mesa seleccionada, código cliente, carrito).
   - Navega a una pantalla bloqueante "Sin servicio" (o similar) que **no** permite volver a interactuar con la app sin recargar la página (`window.location.reload()` como única salida).
4. Nota: dejar comentado en el código que esto es **comportamiento de entorno de pruebas**; en producción el flujo real dependerá de que cada escaneo de QR sea una sesión nueva y no de un guard artificial.

Sugerencia de servicio frontend: `SesionMesaService` con métodos `seleccionarMesa()`, `generarCodigoCliente()`, `cancelarSesion()`, `bloquearAcceso()`.

---

## 4. Flujo 2 — Panel Mesero (`/panel-mesero`, rol `MESERO`)

**Objetivo:** pantalla limpia, sin costos, solo lo operativo.

**Datos a mostrar por pedido activo:**
- Mesa (`lugarAtencion` / `mesa.numero`)
- Código cliente (`CLI-XXXXXX`)
- Lista de productos pedidos (nombre + cantidad, **sin precio**)
- Botón "Servido"

**Historial (columna derecha, compacta):**
- Últimos pedidos con `estado = ENTREGADO`, versión resumida (solo mesa + hora).

**Endpoints necesarios (módulo `pedido`):**
- `GET /api/pedidos/mesero/activos` → pedidos `tipoPedido=MESA`, `estado in (PENDIENTE, EN_PREPARACION, LISTO)`.
- `GET /api/pedidos/mesero/historial?limit=10` → `estado=ENTREGADO`, orden desc.
- `PUT /api/pedidos/{id}/marcar-servido` → valida `estado actual = LISTO`, cambia a `ENTREGADO`, guarda `fechaEntregado`.

**Servicio (`PedidoService` o nuevo `MeseroService`):**
```java
List<PedidoMeseroResponse> listarPedidosActivosMesa();
List<PedidoMeseroResponse> listarHistorialServidos(int limit);
PedidoMeseroResponse marcarComoServido(Long pedidoId);
```

**Tiempo real:** cuando cocina cambia a `LISTO`, notificar por WebSocket/SSE al canal del mesero (`/topic/mesero`) para refrescar sin polling.

---

## 5. Flujo 3 — Panel Cocina (`/panel-cocina`, rol `COCINA`)

**Objetivo:** cola de preparación, productos grandes, coloreados por mesa.

**Datos a mostrar:**
- Todos los productos pendientes de todos los pedidos activos (`PENDIENTE`, `EN_PREPARACION`), **agrupados o etiquetados por mesa/pedido** con un color (no un estado, solo diferenciación visual).
- No hay clasificación roja/verde/naranja aquí (eso es del mesero).
- Botón por producto o por pedido: **"Plato listo"** → transición a `LISTO` (si es por pedido completo) o marca el ítem y cuando todos los ítems del pedido están listos, el pedido pasa a `LISTO` automáticamente.

**Endpoints (módulo `cocina`):**
- `GET /api/cocina/pedidos` → pedidos `estado in (PENDIENTE, EN_PREPARACION)`, incluye detalle de productos.
- `PUT /api/cocina/pedidos/{id}/listo` → `EN_PREPARACION → LISTO`.
- (Opcional, si se quiere granularidad por ítem) `PUT /api/cocina/detalle-pedido/{id}/listo`.

**Servicio (`CocinaService` / `FlujoCocinaService`):**
```java
List<CocinaPedidoResponse> listarColaPreparacion();
CocinaPedidoResponse marcarPedidoListo(Long pedidoId);
```

**Color por mesa (frontend):** función pura `getColorPorMesa(mesaId): string` con paleta fija (hash del id % N colores), aplicada como borde/fondo de la card del producto.

---

## 6. Flujo 4 — Pedido por delivery desde la página principal

### 6.1 Página principal pública
- Sin selección de mesa (eso solo pasa en `/qr/:idMesa`, ver Flujo 1). El pedido creado desde acá siempre es `tipoPedido = DELIVERY`.
- En esta misma página, visible siempre (haya o no horario de atención para delivery), va el botón de **Reservas** (Flujo 7) — reservar una mesa no depende de si el delivery está disponible en ese momento.
- Mostrar solo productos con `disponible = true`.
- Antes de renderizar catálogo, backend valida horario:
  - `ConfiguracionService.estaDentroDeHorarioAtencion(): boolean` usando `horario.apertura` / `horario.cierre`.
  - Si es `false`, el endpoint de productos devuelve `503` (o un flag `disponible=false`) y el frontend muestra únicamente el mensaje **"Sin servicio en este horario"**.

### 6.2 Registro del cliente delivery
Formulario: nombre, teléfono, dirección, descripción/referencia de ubicación.
- Se crea/actualiza un `Usuario` con `rol = CLIENTE`.
- Reutiliza `AuthController.register()` existente, agregando los campos `direccion` y `referenciaUbicacion` al `RegisterRequest`.
- Antes de permitir el registro/pedido, `PedidoService.crearPedido()` valida `usuario.reportado == false`.

### 6.3 Pedido y pago (modo de prueba)
- Endpoint nuevo: `POST /api/pagos/simular` (solo activo en perfil `dev`/`test`):
```java
PagoResponse simularPago(PagoRequest request); // siempre responde APROBADO
```
- Al aprobarse el pago (real o simulado), se crea el `Pedido` con `tipoPedido = DELIVERY`, `estado = CONFIRMADO`, copiando `direccionEntrega`/`referenciaEntrega` desde el `Usuario` (editable en el formulario por si el cliente quiere otra dirección puntual).

### 6.4 Vista de cocina para delivery
- Sección aparte dentro del mismo panel de cocina (columna lateral) que solo trae `tipoPedido = DELIVERY`.
- Los pedidos se muestran con **borde de color** distinto por pedido/cliente (no por mesa, ya que no hay mesa), usando el mismo patrón `getColorPorPedido(pedidoId)`.

---

## 7. Flujo 5 — Panel Delivery / Repartidor (`/panel-delivery`, rol `REPARTIDOR`)

**Datos a mostrar (más completos que el mesero):**
- Nombre del cliente, teléfono, dirección, costo total, pequeña descripción/referencia.
- Botón "ver más" → expande el detalle del pedido (productos, cantidades).
- Botón **"Entregado"** → `EN_CAMINO → ENTREGADO`, pero **el pedido no desaparece** hasta que `confirmadoCliente != null`.

**Endpoints (módulo `delivery`):**
- `GET /api/delivery/pedidos` → `tipoPedido=DELIVERY`, `estado in (LISTO, EN_CAMINO, ENTREGADO)` con `confirmadoCliente = null` (para que no desaparezcan hasta confirmación).
- `PUT /api/delivery/pedidos/{id}/recoger` → `LISTO → EN_CAMINO`.
- `PUT /api/delivery/pedidos/{id}/entregado` → `EN_CAMINO → ENTREGADO`, guarda `fechaEntregado`.

**Servicio (`DeliveryService` / `SeguimientoDeliveryService`):**
```java
List<DeliveryPedidoResponse> listarPedidosEnCurso();
DeliveryPedidoResponse marcarRecogido(Long pedidoId);
DeliveryPedidoResponse marcarEntregado(Long pedidoId);
```

---

## 8. Flujo 6 — Confirmación del cliente delivery

En la página del cliente (accesible por el mismo registro/sesión del pedido):
- Cuando `pedido.estado = ENTREGADO` y `confirmadoCliente = null`, mostrar dos botones:
  - **"Aceptar"** (grande) → `confirmadoCliente = true`, `fechaConfirmacionCliente = now`, pedido pasa a `PAGADO` (cierre normal, dispara generación de `Venta`).
  - **"Reportar"** (pequeño) → `confirmadoCliente = false`, `usuario.reportado = true`, `usuario.fechaReporte = now`.
- Ambos botones hacen desaparecer el widget de confirmación del cliente y, del lado del repartidor, el pedido sale de su lista activa.

**Endpoints (módulo `pedido` o `delivery`):**
- `PUT /api/pedidos/{id}/confirmar-entrega` (cliente autenticado, valida que sea el dueño del pedido).
- `PUT /api/pedidos/{id}/reportar-entrega` (cliente autenticado) → internamente llama a `UsuarioService.marcarComoReportado(usuarioId)`.

**Servicio:**
```java
PedidoResponse confirmarEntregaCliente(Long pedidoId, Long usuarioId);
PedidoResponse reportarEntregaCliente(Long pedidoId, Long usuarioId);
// en UsuarioService
void marcarComoReportado(Long usuarioId);
boolean puedeRealizarPedidos(Long usuarioId); // usado como validación previa en PedidoService
```

---

## 9. Flujo 7 — Reservas desde la página principal

Formulario simplificado: nombre, teléfono, fecha, hora (el monto de adelanto **no se pide**, se calcula).

**Endpoints (módulo `reserva`):**
- `GET /api/reservas/disponibilidad?fecha=&hora=` → mesas libres en ese horario.
- `POST /api/reservas` → crea `Reserva` con `estado = PENDIENTE`, `montoAdelanto` tomado de `Configuracion` (`reserva.monto_adelanto`), y dispara pago (real o simulado, igual que 6.3) para pasar a `CONFIRMADA`.

**Job programado (módulo `reserva`, o `scheduler` en `config/`):**
```java
@Scheduled(fixedRate = 60000) // cada minuto
void actualizarEstadoMesasPorReserva();
```
Lógica:
- Busca reservas `CONFIRMADA` cuya `fechaHora - ahora <= reserva.minutos_bloqueo_mesa` y `fechaHora >= ahora`.
- Marca `mesa.estadoMesa = RESERVADA`.
- Si la reserva ya pasó su hora + margen sin check-in, o fue `CANCELADA`, vuelve `mesa.estadoMesa = LIBRE`.

**Frontend:** el mapa/lista de mesas debe consultar `estadoMesa` para pintar la mesa de rojo cuando esté `RESERVADA`, no solo cuando esté `OCUPADA`.

---

## 10. Resumen de endpoints nuevos por módulo

| Módulo | Endpoint | Rol |
|---|---|---|
| `pedido` | `GET /api/pedidos/mesero/activos` | MESERO |
| `pedido` | `GET /api/pedidos/mesero/historial` | MESERO |
| `pedido` | `PUT /api/pedidos/{id}/marcar-servido` | MESERO |
| `cocina` | `GET /api/cocina/pedidos` | COCINA |
| `cocina` | `PUT /api/cocina/pedidos/{id}/listo` | COCINA |
| `delivery` | `GET /api/delivery/pedidos` | REPARTIDOR |
| `delivery` | `PUT /api/delivery/pedidos/{id}/recoger` | REPARTIDOR |
| `delivery` | `PUT /api/delivery/pedidos/{id}/entregado` | REPARTIDOR |
| `pedido` | `PUT /api/pedidos/{id}/confirmar-entrega` | CLIENTE |
| `pedido` | `PUT /api/pedidos/{id}/reportar-entrega` | CLIENTE |
| `pago` | `POST /api/pagos/simular` (solo dev/test) | CLIENTE |
| `reserva` | `GET /api/reservas/disponibilidad` | público |
| `reserva` | `POST /api/reservas` | público/CLIENTE |
| `configuracion` | `GET /api/configuracion/horario` | público |

Todos deben protegerse en `SecurityConfig` con `hasRole(...)` según la tabla de actores ya definida en `requerimientos.md` (sección 3), siguiendo el mismo patrón que `flujo-autenticacion.md` usa para `/api/usuarios/**`.

---

## 11. Orden sugerido de implementación (coherente con `bloques.md`)

1. **Modelo de datos**: agregar campos nuevos a `Usuario`, `Pedido`, `Mesa`, claves nuevas en `Configuracion`.
2. **Backend módulo `pedido`**: endpoints de mesero (listar activos, historial, marcar servido).
3. **Backend módulo `cocina`**: cola de preparación + marcar listo.
4. **Frontend paneles**: separar rutas por rol usando los guards de Angular que ya deberían existir a partir del JWT (`rol` viene en el token, ver `flujo-autenticacion.md`).
5. **Delivery**: registro de cliente, horario de atención, pago simulado, endpoint de creación de pedido DELIVERY.
6. **Panel repartidor** + confirmación/reporte del cliente.
7. **Reservas**: disponibilidad, creación, job de bloqueo de mesas.
8. **Tiempo real** (WebSocket/SSE) para refrescar mesero/cocina sin recargar.

---

## 12. Pendientes para vos (antes de programar)

- Confirmar si `Pedido.tipoPedido` ya existe en el modelo real o hay que agregarlo desde cero.
- Definir el mecanismo de "sesión" del cliente delivery para que pueda ver su pantalla de confirmación (¿login con teléfono+código?, ¿link único por pedido?).
- Definir cuántos pedidos mostrar en el historial del mesero (ej. 10, 20).
- Confirmar la paleta de colores para mesero (rojo/naranja/verde) y para cocina (colores por mesa) para que frontend y diseño coincidan.

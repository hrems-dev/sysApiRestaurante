# Bloque 2: módulos necesarios

## 1. Producto

### Carpeta

```text
modules/producto/
```

### Necesario

- Entidad `CategoriaProducto`.
- Entidad `Producto`.
- DTOs de categoría y producto.
- Repositorios de categoría y producto.
- Servicio para CRUD de categorías.
- Servicio para CRUD de productos.
- Servicio para actualizar stock y estado.
- Controlador de productos y categorías.
- Pantallas de catálogo y administración.

### Tablas relacionadas

- `CategoriaProducto`
- `Producto`

---

## 2. Pedido

### Carpeta

```text
modules/pedido/
```

### Necesario

- Entidad `Pedido`.
- Entidad `DetallePedido`.
- DTO de pedido y detalle.
- Repositorios.
- Servicio para crear pedido.
- Servicio para agregar o quitar productos.
- Servicio para calcular totales.
- Servicio para cambiar estados del pedido.
- Servicio para asociar pedido a mesa o delivery.
- Controlador de pedidos.
- Pantallas de pedido del cliente y del mesero.

### Tablas relacionadas

- `Pedido`
- `DetallePedido`

---

## 3. Pago

### Carpeta

```text
modules/pago/
```

### Necesario

- Entidad `Pago`.
- Entidad `MetodoPago`.
- Entidad `ReservaPago`.
- DTOs de pago.
- Repositorios.
- Servicio para registrar pago.
- Servicio para validar pago.
- Servicio para simular pasarela.
- Servicio para pago de reserva.
- Servicio para pago mixto.
- Controlador de pagos.
- Pantallas de pago simulado y validación.

### Tablas relacionadas

- `MetodoPago`
- `Pago`
- `ReservaPago`

---

## 4. Reserva

### Carpeta

```text
modules/reserva/
```

### Necesario

- Entidad `Reserva`.
- Entidad `ReservaPago`.
- DTOs de reserva.
- Repositorios.
- Servicio para crear reserva.
- Servicio para validar disponibilidad.
- Servicio para confirmar reserva con adelanto.
- Servicio para cancelar reserva.
- Servicio para aplicar penalidad o devolución.
- Controlador de reservas.
- Pantallas de reserva.

### Tablas relacionadas

- `Reserva`
- `ReservaPago`

---

## 5. Venta

### Carpeta

```text
modules/venta/
```

### Necesario

- Entidad `Venta`.
- Entidad `DocVenta`.
- DTOs de venta.
- Repositorios.
- Servicio para generar venta desde pedido pagado.
- Servicio para emitir documento.
- Servicio para cerrar venta.
- Servicio para anular venta.
- Controlador de ventas.
- Pantallas de facturación y venta.

### Tablas relacionadas

- `Venta`
- `DocVenta`

---

## 6. Notificación

### Carpeta

```text
modules/notificacion/
```

### Necesario

- Entidad `Notificacion`.
- DTOs de notificación.
- Repositorio.
- Servicio para generar notificaciones.
- Servicio para marcar leído.
- Controlador de notificaciones.
- Integración con pedido, reserva, pago y venta.

### Tablas relacionadas

- `Notificacion`

---

## 7. Cocina

### Carpeta

```text
modules/cocina/
```

### Necesario

- DTO de pedidos para cocina.
- Servicio para filtrar pedidos pagados.
- Servicio para cambiar estados de preparación.
- Controlador de cocina.
- Vista de cola de pedidos.
- No requiere nuevas tablas, usa `Pedido` y `DetallePedido`.

### Tablas relacionadas

- `Pedido`
- `DetallePedido`
- `Pago`
- `Venta`

---

## 8. Contabilidad

### Carpeta

```text
modules/contabilidad/
```

### Necesario

- DTOs financieros.
- Servicio para ventas diarias.
- Servicio para pagos confirmados.
- Servicio para anulaciones y reembolsos.
- Servicio para cierres de caja.
- Controlador de contabilidad.
- Pantallas de resumen financiero.
- Usa `Pago`, `Venta`, `DocVenta`, `ReservaPago`.

### Tablas relacionadas

- `Pago`
- `Venta`
- `DocVenta`
- `ReservaPago`

---

## 9. Delivery

### Carpeta

```text
modules/delivery/
```

### Necesario

- DTOs de delivery.
- Servicio para asignar repartidor.
- Servicio para seguimiento de entrega.
- Controlador de delivery.
- Pantallas de estado de entrega.
- Usa `Pedido` y `Usuario`.

### Tablas relacionadas

- `Pedido`
- `Usuario`

---

## 10. Facturación

### Carpeta

```text
modules/facturacion/
```

### Necesario

- DTOs de comprobante.
- Servicio para generar boleta, factura o ticket.
- Servicio para correlativos.
- Controlador de facturación.
- Pantallas de facturas y comprobantes.
- Usa `Venta` y `DocVenta`.

### Tablas relacionadas

- `Venta`
- `DocVenta`

---

## 11. Reportes

### Carpeta

```text
modules/reportes/
```

### Necesario

- DTOs de reportes.
- Servicio para ventas.
- Servicio para pedidos.
- Servicio para reservas.
- Servicio para entregas.
- Servicio para mesas.
- Controlador de reportes.
- Pantallas de reportes generales.
- Usa tablas transaccionales.

### Tablas relacionadas

- `Pedido`
- `Pago`
- `Reserva`
- `Venta`
- `Notificacion`
- `LugarAtencion`

---

# Bloque 2 completo

## Módulos a crear en este bloque

```text
- producto
- pedido
- pago
- reserva
- venta
- notificacion
- cocina
- contabilidad
- delivery
- facturacion
- reportes
```

---

# Orden interno de construcción del bloque 2

```text
1. categoria-producto
2. producto
3. pedido
4. detalle-pedido
5. metodo-pago
6. pago
7. reserva
8. reserva-pago
9. venta
10. doc-venta
11. notificacion
12. cocina
13. contabilidad
14. delivery
15. facturacion
16. reportes
```

---

# Dependencias del bloque 2

```text
Necesita bloque 1:
- Usuario
- Rol
- LugarAtencion
- QRLugar
- Seguridad JWT
- Configuración general

Base de este bloque:
- CategoriaProducto
- Producto
- Pedido
- DetallePedido
- MetodoPago
- Pago
- Reserva
- ReservaPago
- Venta
- DocVenta
- Notificacion
```

---

# Resultado esperado del bloque 2

```text
- CRUD de productos
- creación y cálculo de pedidos
- pagos simulados y validación
- reservas con adelanto
- generación de venta y documento
- notificaciones
- cola de cocina
- cierres y reportes contables
- delivery
- facturación
- paneles operativos
```

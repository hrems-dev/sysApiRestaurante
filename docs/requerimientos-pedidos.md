# Requerimientos del Módulo de Pedidos

## 1. Visión General

El módulo de pedidos gestiona la creación, seguimiento y procesamiento de pedidos en el restaurante. Soporta dos tipos de atención: **MESA** (presencial) y **DELIVERY** (a domicilio).

---

## 2. Entidades Principales

### Pedido
| Campo | Tipo | Descripción |
|-------|------|-------------|
| idPedido | number | PK autoincremental |
| idUsuario | number | Usuario que crea el pedido (0 = cliente público) |
| idLugar | number | FK a LugarAtencion (mesa) - NULL para delivery |
| tipoPedido | enum | `MESA` \| `DELIVERY` |
| direccionEntrega | string | N° mesa (ej: "Mesa 12") o dirección completa |
| observacionPedido | string | Notas adicionales |
| estadoPedido | enum | `PENDIENTE` \| `EN_PREPARACION` \| `LISTO` \| `ENTREGADO` \| `CANCELADO` \| `PAGADO` |
| subtotal | decimal | Suma de items |
| igv | decimal | 18% |
| total | decimal | subtotal + igv |
| fechaPedido | datetime | Timestamp creación |
| fechaActualizacion | datetime | Último cambio de estado |

### DetallePedido
| Campo | Tipo | Descripción |
|-------|------|-------------|
| idDetalle | number | PK |
| idPedido | number | FK a Pedido |
| idProducto | number | FK a Producto |
| cantidad | number | Cantidad solicitada |
| precioUnitario | decimal | Precio al momento del pedido |
| subtotal | decimal | cantidad * precioUnitario |
| observacion | string | Notas del item (ej: "sin cebolla") |

---

## 3. Tipos de Pedido

### 3.1 Pedido MESA (Presencial)
- **Origen**: Menú público (cliente escanea QR en mesa) o Mesero
- **idLugar**: Requerido (mesa física)
- **direccionEntrega**: "Mesa X" o nombre del lugar
- **Flujo**: PENDIENTE → EN_PREPARACION → LISTO → ENTREGADO → PAGADO

### 3.2 Pedido DELIVERY (A domicilio)
- **Origen**: App delivery / Web / Teléfono
- **idLugar**: NULL
- **direccionEntrega**: Dirección completa cliente
- **Requiere**: Asignación de repartidor, seguimiento GPS
- **Flujo**: PENDIENTE → CONFIRMADO → EN_PREPARACION → LISTO → EN_CAMINO → ENTREGADO → PAGADO

---

## 4. Servicios Requeridos (Backend)

### PedidoService
- `create(data: PedidoRequest): PedidoResponse` - Crear pedido con items
- `findById(id: number): PedidoResponse` - Obtener con items
- `findAll(filtros): Paginated<PedidoResponse>` - Listar con filtros
- `updateEstado(id, estado): PedidoResponse` - Cambiar estado
- `agregarProducto(idPedido, item): DetallePedidoResponse` - Agregar item
- `quitarProducto(idPedido, idProducto): void` - Quitar item
- `calcularTotales(idPedido): TotalesResponse` - Recalcular

### DetallePedidoService
- `create(data: DetallePedidoRequest): DetallePedidoResponse`
- `update(idDetalle, data): DetallePedidoResponse`
- `delete(idDetalle): void`

### EstadoPedidoService
- `validarTransicion(estadoActual, nuevoEstado): boolean`
- `getEstadosValidos(estadoActual): EstadoPedido[]`
- `procesarCambioEstado(pedido, nuevoEstado): void` - Side effects (notificaciones, cocina, etc.)

---

## 5. DTOs

### PedidoRequest
```typescript
interface PedidoRequest {
  idUsuario: number;
  idLugar?: number;           // Requerido si tipoPedido = MESA
  tipoPedido: 'MESA' | 'DELIVERY';
  direccionEntrega: string;   // "Mesa 12" o dirección completa
  observacionPedido?: string;
  detalles: DetallePedidoRequest[];
}
```

### DetallePedidoRequest
```typescript
interface DetallePedidoRequest {
  idProducto: number;
  cantidad: number;
  precioUnitario: number;
  observacion?: string;
}
```

### PedidoResponse
```typescript
interface PedidoResponse {
  idPedido: number;
  idUsuario: number;
  idLugar?: number;
  tipoPedido: 'MESA' | 'DELIVERY';
  direccionEntrega: string;
  observacionPedido: string;
  estadoPedido: EstadoPedido;
  subtotal: number;
  igv: number;
  total: number;
  fechaPedido: string;
  detalles: DetallePedidoResponse[];
  lugar?: LugarAtencionResponse;  // Solo si MESA
  repartidor?: UsuarioResponse;   // Solo si DELIVERY
}
```

---

## 6. Estados del Pedido

```
MESA:           PENDIENTE → EN_PREPARACION → LISTO → ENTREGADO → PAGADO
                        ↓                          ↓
                     CANCELADO                 CANCELADO

DELIVERY:       PENDIENTE → CONFIRMADO → EN_PREPARACION → LISTO → EN_CAMINO → ENTREGADO → PAGADO
                        ↓                          ↓                    ↓
                     CANCELADO                 CANCELADO          CANCELADO
```

### Reglas de transición
| Estado Actual | Permitidos |
|---------------|------------|
| PENDIENTE | EN_PREPARACION, CONFIRMADO (delivery), CANCELADO |
| CONFIRMADO | EN_PREPARACION, CANCELADO |
| EN_PREPARACION | LISTO, CANCELADO |
| LISTO | ENTREGADO, EN_CAMINO (delivery), CANCELADO |
| EN_CAMINO | ENTREGADO, CANCELADO |
| ENTREGADO | PAGADO |
| PAGADO | (terminal) |
| CANCELADO | (terminal) |

---

## 7. API Endpoints

| Método | Endpoint | Descripción | Roles |
|--------|----------|-------------|-------|
| POST | `/api/pedidos` | Crear pedido | PUBLIC, MESERO, ADMIN |
| GET | `/api/pedidos` | Listar con filtros | ADMIN, MESERO, COCINA |
| GET | `/api/pedidos/:id` | Ver detalle | ALL |
| PUT | `/api/pedidos/:id/estado` | Cambiar estado | MESERO, COCINA, ADMIN |
| POST | `/api/pedidos/:id/productos` | Agregar producto | MESERO, PUBLIC |
| DELETE | `/api/pedidos/:id/productos/:idProducto` | Quitar producto | MESERO, PUBLIC |
| GET | `/api/pedidos/mesa/:idLugar` | Pedidos por mesa | MESERO, COCINA |
| GET | `/api/pedidos/pendientes` | Pedidos en cocina | COCINA |

---

## 8. Flujo Menú Público (Cliente)

1. **Cliente entra** → Página menú público
2. **Identifica mesa**:
   - Escanea QR en mesa → Auto-selecciona mesa
   - O "Agregar manual" → Modal lista mesas disponibles
3. **Navega productos** → Agrega al carrito
4. **Revisa carrito** → Cantidades, totales
5. **Envía pedido** → POST `/api/pedidos` con:
   ```json
   {
     "idUsuario": 0,
     "idLugar": 5,
     "tipoPedido": "MESA",
     "direccionEntrega": "Mesa 12",
     "observacionPedido": "Pedido desde Menú Público - Mesa 12",
     "detalles": [...]
   }
   ```
6. **Confirmación** → "Pedido enviado a cocina"
7. **Seguimiento** → Estado en tiempo real (opcional)

---

## 9. Flujo Mesero (App Interna)

1. **Mesero accede** → Panel mesero
2. **Selecciona mesa** → Lista mesas ocupadas/libres
3. **Crea pedido** → Busca productos, agrega items
4. **Envía a cocina** → Cambia estado a EN_PREPARACION
5. **Gestiona** → Agrega/quita items, ve estado
6. **Entrega** → Marca ENTREGADO
7. **Cobro** → Genera venta + pago

---

## 10. Flujo Cocina

1. **Cocina ve** → Cola de pedidos PENDIENTE/EN_PREPARACION
2. **Filtra** → Por tipo (MESA/DELIVERY), prioridad
3. **Prepara** → Marca EN_PREPARACION → LISTO
4. **Notifica** → Push a mesero/repartidor cuando LISTO

---

## 11. Flujo Delivery

1. **Pedido entra** → Tipo DELIVERY
2. **Admin/Operador** → Confirma pedido (CONFIRMADO)
3. **Cocina** → Prepara (EN_PREPARACION → LISTO)
4. **Asigna repartidor** → Cambia a EN_CAMINO
5. **Repartidor** → Recoge → Entrega → Marca ENTREGADO
6. **Pago** → Contraentrega u online → PAGADO

---

## 12. Integraciones

| Módulo | Integración |
|--------|-------------|
| **Producto** | Validar stock, obtener precio, categoría |
| **LugarAtencion** | Validar mesa existe y está activa (MESA) |
| **Usuario** | Mesero creador, repartidor asignado, cliente |
| **Pago** | Registrar pago, validar monto, métodos |
| **Venta** | Generar venta al pagar (PAGADO → Venta) |
| **Facturación** | Emitir boleta/factura desde venta |
| **Notificación** | Push: pedido nuevo, listo, entregado, cancelado |
| **Cocina** | Cola de pedidos, tiempos de preparación |
| **Reporte** | Ventas, pedidos por mesa, tiempos, cancelados |

---

## 13. Validaciones de Negocio

- **MESA**: `idLugar` obligatorio, debe estar `estadoLugar = true`, `tipoLugar = 'MESA'`
- **DELIVERY**: `idLugar = null`, `direccionEntrega` completa obligatoria
- **Stock**: Validar disponibilidad al agregar items (opcional: solo warning)
- **Precios**: Usar precio actual del producto al momento del pedido
- **Totales**: Recalcular automáticamente en cada cambio
- **Estados**: Solo transiciones válidas según máquina de estados

---

## 14. Pantallas Frontend Requeridas

| Pantalla | Ruta | Descripción |
|----------|------|-------------|
| Menú Público | `/menu` | Cliente escanea QR, ve productos, hace pedido |
| Modal Mesas | Modal | Lista mesas disponibles para selección manual |
| Panel Mesero | `/mesero/pedidos` | Gestión mesas, crear/editar pedidos |
| Detalle Pedido | `/mesero/pedidos/:id` | Ver/editar items, cambiar estado |
| Cocina | `/cocina` | Cola pedidos, marcar preparación |
| Delivery | `/delivery` | Gestión repartidores, seguimiento |
| Historial | `/pedidos/historial` | Filtros por fecha, mesa, estado, tipo |

---

## 15. Consideraciones Técnicas

- **Concurrencia**: Optimistic locking en Pedido (version/timestamp)
- **Tiempo real**: WebSocket/SSE para estados en cocina/mesero
- **Offline**: Service Worker para menú público (cache productos)
- **Audit**: Log de cambios de estado (quién, cuándo, estado anterior/nuevo)
- **Performance**: Índices en `estadoPedido`, `tipoPedido`, `fechaPedido`, `idLugar`

---

## 16. Tablas / Entidades de Base de Datos

### 16.1 Esquema Relacional

```sql
-- Tabla principal de pedidos
CREATE TABLE pedido (
    id_pedido         BIGSERIAL PRIMARY KEY,
    id_usuario        BIGINT NOT NULL DEFAULT 0,
    id_lugar          BIGINT NULL,                    -- FK a lugar_atencion (NULL para delivery)
    tipo_pedido       VARCHAR(20) NOT NULL,           -- 'MESA' | 'DELIVERY'
    direccion_entrega VARCHAR(500) NOT NULL,          -- "Mesa 12" o dirección completa
    observacion_pedido TEXT,
    estado_pedido     VARCHAR(30) NOT NULL DEFAULT 'PENDIENTE',
    subtotal          DECIMAL(12,2) NOT NULL DEFAULT 0,
    igv               DECIMAL(12,2) NOT NULL DEFAULT 0,
    total             DECIMAL(12,2) NOT NULL DEFAULT 0,
    fecha_pedido      TIMESTAMP NOT NULL DEFAULT NOW(),
    fecha_actualizacion TIMESTAMP NOT NULL DEFAULT NOW(),
    version           BIGINT NOT NULL DEFAULT 0        -- Optimistic locking
);

-- Índices
CREATE INDEX idx_pedido_estado ON pedido(estado_pedido);
CREATE INDEX idx_pedido_tipo ON pedido(tipo_pedido);
CREATE INDEX idx_pedido_fecha ON pedido(fecha_pedido);
CREATE INDEX idx_pedido_lugar ON pedido(id_lugar);
CREATE INDEX idx_pedido_usuario ON pedido(id_usuario);

-- Tabla de detalles de pedido
CREATE TABLE detalle_pedido (
    id_detalle        BIGSERIAL PRIMARY KEY,
    id_pedido         BIGINT NOT NULL REFERENCES pedido(id_pedido) ON DELETE CASCADE,
    id_producto       BIGINT NOT NULL REFERENCES producto(id_producto),
    cantidad          INT NOT NULL DEFAULT 1,
    precio_unitario   DECIMAL(12,2) NOT NULL,
    subtotal          DECIMAL(12,2) NOT NULL,
    observacion       TEXT
);

CREATE INDEX idx_detalle_pedido ON detalle_pedido(id_pedido);
CREATE INDEX idx_detalle_producto ON detalle_pedido(id_producto);

-- Tabla de lugares de atención (mesas, salones, delivery zones)
CREATE TABLE lugar_atencion (
    id_lugar          BIGSERIAL PRIMARY KEY,
    nombre_lugar      VARCHAR(100) NOT NULL,          -- "Mesa 1", "Salón VIP", "Delivery Zona Norte"
    tipo_lugar        VARCHAR(30) NOT NULL,           -- 'MESA' | 'SALON' | 'DELIVERY_ZONE' | 'BARRA'
    direccion         VARCHAR(500),
    capacidad_maxima  INT,
    estado_lugar      BOOLEAN NOT NULL DEFAULT true,
    observacion       TEXT,
    codigo_qr         VARCHAR(100) UNIQUE,            -- Código QR único para la mesa
    url_qr            VARCHAR(500),                   -- URL completa del menú público + mesa
    fecha_creacion    TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_lugar_tipo ON lugar_atencion(tipo_lugar);
CREATE INDEX idx_lugar_estado ON lugar_atencion(estado_lugar);
CREATE INDEX idx_lugar_qr ON lugar_atencion(codigo_qr);
```

### 16.2 Entidades JPA (Backend)

```java
// Pedido.java
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;
    
    private Long idUsuario;
    private Long idLugar;                    // FK a LugarAtencion (nullable para delivery)
    
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TipoPedido tipoPedido;           // MESA | DELIVERY
    
    private String direccionEntrega;
    private String observacionPedido;
    
    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private EstadoPedido estadoPedido;       // PENDIENTE, EN_PREPARACION, LISTO, ENTREGADO, CANCELADO, PAGADO
    
    private BigDecimal subtotal;
    private BigDecimal igv;
    private BigDecimal total;
    
    private LocalDateTime fechaPedido;
    private LocalDateTime fechaActualizacion;
    
    @Version
    private Long version;                    // Optimistic locking
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_lugar", insertable = false, updatable = false)
    private LugarAtencion lugar;
}

// DetallePedido.java
@Entity
@Table(name = "detalle_pedido")
public class DetallePedido {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetalle;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pedido", nullable = false)
    private Pedido pedido;
    
    private Long idProducto;
    private Integer cantidad;
    private BigDecimal precioUnitario;
    private BigDecimal subtotal;
    private String observacion;
}

// LugarAtencion.java
@Entity
@Table(name = "lugar_atencion")
public class LugarAtencion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLugar;
    
    private String nombreLugar;              -- "Mesa 1", "Mesa 2", etc.
    private String tipoLugar;                -- MESA, SALON, DELIVERY_ZONE, BARRA
    private String direccion;
    private Integer capacidadMaxima;
    private Boolean estadoLugar;             -- true = activo/disponible
    private String observacion;
    private String codigoQR;                 -- Código único: "MESA:1" o UUID
    private String urlQR;                    -- "https://restaurante.com/menu?mesa=1"
    private LocalDateTime fechaCreacion;
    
    @OneToMany(mappedBy = "lugar")
    private List<Pedido> pedidos;
}

// QRLugar.java (Códigos QR por mesa)
@Entity
@Table(name = "qr_lugar")
public class QRLugar {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idQR;
    
    private Long idLugar;                    -- FK a lugar_atencion
    private String codigoQR;                 -- Código escaneable: "MESA:12"
    private String urlQR;                    -- URL completa del menú público
    private Boolean estadoQR;                -- Activo/Inactivo
    private LocalDateTime fechaGeneracion;
}
```

---

## 17. Flujo de Escaneo QR - Qué Tabla/Entidad Consulta

### 17.1 Cuando el Cliente Escanea el QR en la Mesa

```
┌─────────────────────────────────────────────────────────────────┐
│  QR CONTENIDO: "MESA:12"  o  URL: "https://.../menu?mesa=12"  │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│  FRONTEND (menu-publico.component.ts)                           │
│  1. Extrae "12" del texto QR (regex: /MESA[:=]?\s*(\w+)/i)    │
│  2. Busca en this.lugares[] cargado en ngOnInit:               │
│     const lugar = this.lugares.find(l =>                       │
│       l.nombreLugar === "Mesa 12" && l.tipoLugar === "MESA"   │
│     );                                                          │
│  3. Si encuentra → setea mesaIdentificada + idLugarSeleccionado │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│  BACKEND - NO SE CONSULTA AL ESCANEAR (offline-first)           │
│                                                                 │
│  Los lugares ya vienen cargados via:                           │
│  GET /api/lugares/activos  →  LugarService.findAllActive()     │
│                                                                 │
│  Consulta SQL equivalente:                                     │
│  SELECT * FROM lugar_atencion                                   │
│  WHERE estado_lugar = true AND tipo_lugar = 'MESA';            │
└─────────────────────────────────────────────────────────────────┘
```

### 17.2 Validación Backend al Crear Pedido (POST /api/pedidos)

```java
// PedidoService.create()
@Transactional
public PedidoResponse create(PedidoRequest request) {
    // 1. VALIDAR MESA EXISTE Y ESTÁ ACTIVA
    if (request.getTipoPedido() == TipoPedido.MESA) {
        LugarAtencion lugar = lugarRepository.findById(request.getIdLugar())
            .orElseThrow(() -> new RecursoNoEncontradoException("Mesa no existe"));
        
        if (!lugar.getEstadoLugar()) {
            throw new NegocioException("Mesa no disponible");
        }
        if (!"MESA".equals(lugar.getTipoLugar())) {
            throw new NegocioException("El lugar no es una mesa válida");
        }
        // Verificar que no tenga pedido activo (opcional)
        validarMesaLibre(request.getIdLugar());
    }
    
    // 2. CREAR PEDIDO
    Pedido pedido = new Pedido();
    pedido.setIdUsuario(request.getIdUsuario());
    pedido.setIdLugar(request.getIdLugar());
    pedido.setTipoPedido(request.getTipoPedido());
    pedido.setDireccionEntrega(request.getDireccionEntrega());
    pedido.setObservacionPedido(request.getObservacionPedido());
    pedido.setEstadoPedido(EstadoPedido.PENDIENTE);
    pedido.setFechaPedido(LocalDateTime.now());
    
    // 3. PROCESAR DETALLES
    for (DetallePedidoRequest det : request.getDetalles()) {
        Producto producto = productoRepository.findById(det.getIdProducto())
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto no existe"));
        
        validarStock(producto, det.getCantidad());
        
        DetallePedido detalle = new DetallePedido();
        detalle.setPedido(pedido);
        detalle.setIdProducto(producto.getIdProducto());
        detalle.setCantidad(det.getCantidad());
        detalle.setPrecioUnitario(producto.getPrecioProducto()); // Precio actual
        detalle.setSubtotal(producto.getPrecioProducto().multiply(BigDecimal.valueOf(det.getCantidad())));
        detalle.setObservacion(det.getObservacion());
        
        pedido.getDetalles().add(detalle);
    }
    
    // 4. CALCULAR TOTALES
    calcularTotales(pedido);
    
    // 5. GUARDAR
    return pedidoRepository.save(pedido);
}
```

### 17.3 Tablas Consultadas en Orden

| Paso | Tabla | Operación | Propósito |
|------|-------|-----------|-----------|
| 1 | `lugar_atencion` | SELECT (carga inicial) | Obtener mesas activas para modal |
| 2 | `lugar_atencion` | SELECT (validación) | Verificar mesa existe, activa, tipo MESA |
| 3 | `producto` | SELECT (por cada item) | Obtener precio actual, validar stock |
| 4 | `pedido` | INSERT | Crear cabecera pedido |
| 5 | `detalle_pedido` | INSERT (batch) | Guardar items |
| 6 | `lugar_atencion` | UPDATE (opcional) | Marcar mesa como ocupada |

---

## 18. Flujos Completos por Modelo/Entidad

### 18.1 Flujo: LugarAtencion (Mesa)

```
CREACIÓN (Admin/Onboarding)
─────────────────────────────────────────────────────────────
1. Admin → Onboarding → "Crear Mesa"
2. POST /api/lugares { nombre: "Mesa 12", tipo: "MESA", capacidad: 4 }
3. Backend:
   - Inserta en lugar_atencion
   - Genera codigoQR = "MESA:12"
   - Genera urlQR = "https://dominio.com/menu?mesa=12"
   - Guarda QRLugar (codigoQR, urlQR, idLugar)
4. Imprime QR físico → Lo pone en la mesa

USO (Cliente)
─────────────────────────────────────────────────────────────
1. Cliente escanea QR → Lee "MESA:12"
2. Frontend busca en lugares[] cargados → Encuentra idLugar=5
3. Cliente agrega productos → Carrito
4. POST /api/pedidos { idLugar: 5, tipoPedido: "MESA", ... }
5. Backend valida lugar_atencion.id=5, estado=true, tipo=MESA
6. Crea pedido + detalles
7. (Opcional) Actualiza lugar_atencion → estado_lugar=false (ocupada)

LIBERACIÓN
─────────────────────────────────────────────────────────────
1. Pedido → ENTREGADO → PAGADO
2. Backend: UPDATE lugar_atencion SET estado_lugar=true WHERE id=5
3. Mesa disponible para nuevo cliente
```

### 18.2 Flujo: Pedido (MESA)

```
INICIO
─────────────────────────────────────────────────────────────
Cliente en menú público
    │
    ├── Escanea QR → Auto-selecciona mesa (idLugar)
    └── O Manual → Modal → Click mesa → Selecciona idLugar
    │
    ▼
AGREGAR PRODUCTOS
─────────────────────────────────────────────────────────────
1. GET /api/productos → Lista productos activos
2. Click "+" → Agrega a carrito local (memoria)
3. Repite para múltiples items
4. Carrito muestra: items, cantidades, subtotal, total

ENVÍO
─────────────────────────────────────────────────────────────
POST /api/pedidos
{
  "idUsuario": 0,
  "idLugar": 5,
  "tipoPedido": "MESA",
  "direccionEntrega": "Mesa 12",
  "observacionPedido": "Pedido desde Menú Público - Mesa 12",
  "detalles": [
    {"idProducto": 10, "cantidad": 2, "precioUnitario": 15.50},
    {"idProducto": 23, "cantidad": 1, "precioUnitario": 8.00}
  ]
}

BACKEND PROCESSA:
├── Valida mesa (lugar_atencion)
├── Valida productos existen y tienen stock
├── Calcula totales (subtotal + 18% IGV)
├── Inserta pedido (estado=PENDIENTE)
├── Inserta detalle_pedido (2 registros)
└── Retorna PedidoResponse con idPedido

CONFIRMACIÓN CLIENTE
─────────────────────────────────────────────────────────────
"¡Pedido enviado! ID: 12345 - Mesa 12"
Botón "Ver estado" → Polling GET /api/pedidos/12345

ESTADOS EN COCINA/MESERO
─────────────────────────────────────────────────────────────
PENDIENTE → (Cocina ve) → EN_PREPARACION → LISTO
    │                                        │
    └── (Mesero entrega) → ENTREGADO → (Caja) → PAGADO
                                                    │
                                                    ▼
                                            GENERA VENTA
                                            (módulo venta)
```

### 18.3 Flujo: DetallePedido

```
AGREGAR ITEM (en carrito o mesero editando)
─────────────────────────────────────────────────────────────
1. Usuario click "+" en producto
2. Frontend: carrito.push({ producto, cantidad: 1 })
3. Si ya existe → cantidad++

ENVIAR PEDIDO
─────────────────────────────────────────────────────────────
Frontend serializa carrito → detalles[]
Backend: Por cada detalle:
  - Busca producto (precio actual)
  - Calcula subtotal = precio * cantidad
  - Inserta detalle_pedido

MODIFICAR CANTIDAD (Mesero / Cliente antes de enviar)
─────────────────────────────────────────────────────────────
Frontend: item.cantidad = nuevaCantidad
Recalcula total local

ELIMINAR ITEM
─────────────────────────────────────────────────────────────
Frontend: carrito.splice(index, 1)
Si pedido ya enviado:
  DELETE /api/pedidos/{id}/productos/{idProducto}
  Backend: 
    - Borra detalle_pedido
    - Recalcula totales pedido
    - Si 0 items → Cancela pedido (opcional)
```

### 18.4 Flujo: Producto (en contexto de Pedido)

```
CATÁLOGO MENÚ PÚBLICO
─────────────────────────────────────────────────────────────
GET /api/productos?estado=true
→ Filtra: estadoProducto = true, stock > 0 (opcional)
→ Agrupa por categoria (CategoriaProducto)
→ Frontend renderiza grid por categorías

PRECIO EN PEDIDO
─────────────────────────────────────────────────────────────
IMPORTANTE: Precio se congela al momento del pedido
- producto.precioProducto al momento del POST
- No cambia si admin modifica precio después
- Histórico en detalle_pedido.precio_unitario

STOCK (Opcional)
─────────────────────────────────────────────────────────────
Validación en PedidoService.create():
  if (producto.getStock() < cantidadSolicitada) {
    throw new NegocioException("Stock insuficiente: " + producto.getNombreProducto());
  }
  // NO descuenta stock aquí (solo al ENTREGADO/PAGADO vía Venta)
```

### 18.5 Flujo: Usuario (Mesero / Cliente / Repartidor)

```
MESERO (App Interna)
─────────────────────────────────────────────────────────────
Login → JWT con role=MESERO
GET /api/lugares?tipo=MESA → Lista mesas
Click mesa → Ve pedidos activos (GET /api/pedidos/mesa/{id})
  - Crear pedido para mesa
  - Agregar/quitar items
  - Cambiar estado: PENDIENTE → EN_PREPARACION
  - Marcar ENTREGADO
  - Cobrar → POST /api/pagos

CLIENTE PÚBLICO (Sin login)
─────────────────────────────────────────────────────────────
Escanea QR → Menú público (PUBLIC access)
No requiere autenticación
idUsuario = 0 en pedido
Solo tipo MESA permitido

REPARTIDOR (Delivery)
─────────────────────────────────────────────────────────────
Login → role=REPARTIDOR
GET /api/pedidos?tipo=DELIVERY&estado=EN_CAMINO
Click pedido → Ve dirección, cliente, items
Cambia: EN_CAMINO → ENTREGADO
Confirma pago contraentrega (si aplica)
```

### 18.6 Flujo: EstadoPedido (Máquina de Estados)

```
VALIDACIÓN DE TRANSICIONES
─────────────────────────────────────────────────────────────
EstadoPedidoService.validarTransicion(actual, nuevo):

MESA:
PENDIENTE → EN_PREPARACION ✓
PENDIENTE → CANCELADO ✓
EN_PREPARACION → LISTO ✓
EN_PREPARACION → CANCELADO ✓ (si cocina rechaza)
LISTO → ENTREGADO ✓
LISTO → CANCELADO ✓ (cliente cancela)
ENTREGADO → PAGADO ✓
ENTREGADO → CANCELADO ✗ (ya entregado)

DELIVERY:
PENDIENTE → CONFIRMADO ✓
PENDIENTE → CANCELADO ✓
CONFIRMADO → EN_PREPARACION ✓
EN_PREPARACION → LISTO ✓
LISTO → EN_CAMINO ✓ (repartidor asignado)
EN_CAMINO → ENTREGADO ✓
ENTREGADO → PAGADO ✓

SIDE EFFECTS POR ESTADO
─────────────────────────────────────────────────────────────
EN_PREPARACION:
  - Notifica a cocina (WebSocket)
  - Inicia timer preparación

LISTO:
  - Notifica a mesero/repartidor
  - Si MESO: mesa lista para servir
  - Si DELIVERY: notifica a logística

ENTREGADO:
  - Si MESA: Libera mesa (lugar_atencion.estado_lugar=true)
  - Registra timestamp entrega

PAGADO:
  - Genera Venta (módulo venta)
  - Genera Documento (módulo facturación)
  - Actualiza reportes
  - Notifica a contabilidad

CANCELADO:
  - Libera mesa si estaba ocupada
  - Notifica a cocina (cancelar preparación)
  - Log motivo cancelación
```

---

## 19. Resumen de Tablas por Operación

| Operación | Tablas Leídas | Tablas Escritas |
|-----------|---------------|-----------------|
| Cargar mesas (modal) | `lugar_atencion` | - |
| Escanear QR (frontend) | `lugar_atencion` (cache) | - |
| Validar mesa al crear pedido | `lugar_atencion` | - |
| Obtener productos | `producto`, `categoria_producto` | - |
| Crear pedido | `lugar_atencion`, `producto` | `pedido`, `detalle_pedido` |
| Agregar item a pedido | `producto`, `pedido` | `detalle_pedido` |
| Cambiar estado | `pedido` | `pedido` (+ audit log) |
| Pagar pedido | `pedido`, `metodo_pago` | `pago`, `venta`, `doc_venta` |
| Liberar mesa | - | `lugar_atencion` (estado_lugar) |
| Reportes | `pedido`, `detalle_pedido`, `producto`, `lugar_atencion`, `pago`, `venta` | - |

---

## 20. Diccionario de Datos Completo

| Tabla | PK | FKs | Índices Clave | Descripción |
|-------|----|-----|---------------|-------------|
| `pedido` | id_pedido | id_usuario, id_lugar | estado, tipo, fecha, lugar | Cabecera pedido |
| `detalle_pedido` | id_detalle | id_pedido, id_producto | pedido, producto | Items del pedido |
| `lugar_atencion` | id_lugar | - | tipo, estado, codigo_qr | Mesas, salones, delivery zones |
| `qr_lugar` | id_qr | id_lugar | codigo_qr | Códigos QR por mesa |
| `producto` | id_producto | id_categoria | estado, categoria | Catálogo productos |
| `categoria_producto` | id_categoria | - | estado | Categorías menú |
| `usuario` | id_usuario | id_rol | email, rol | Meseros, repartidores, admins |
| `venta` | id_venta | id_pedido, id_usuario | fecha, estado | Venta generada al pagar |
| `pago` | id_pago | id_venta, id_metodo | venta, fecha | Pagos de venta |
| `doc_venta` | id_doc | id_venta | serie, correlativo | Boleta/Factura/Ticket |

---

*Documento generado automáticamente - Actualizar según cambios en entidad/negocio*
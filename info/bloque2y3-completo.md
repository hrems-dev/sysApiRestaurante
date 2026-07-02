# Bloques 2 y 3 - Completo

## Resumen de módulos implementados

| Módulo | Bloque | Archivos | Estado |
|--------|--------|----------|--------|
| producto | 2 | 13 | ✅ |
| pedido | 2 | 14 | ✅ |
| pago | 2 | 10 | ✅ |
| reserva | 2 | 13 | ✅ |
| venta | 2 | 13 | ✅ |
| notificacion | 2 | 7 | ✅ |
| cocina | 2/3 | 7 | ✅ |
| contabilidad | 2/3 | 7 | ✅ |
| delivery | 2/3 | 6 | ✅ |
| facturacion | 2/3 | 6 | ✅ |
| reportes | 2/3 | 10 | ✅ |

**Total: ~106 archivos nuevos creados**

---

## Estructura modular completa

```
src/main/java/pe/edu/upeu/sys_api_restaurant/modules/
├── autenticacion/
│   ├── controller/SesionController.java
│   ├── dto/LogoutRequest.java, RefreshTokenRequest.java
│   └── service/AuthService.java
│
├── onboarding/
│   ├── controller/OnboardingController.java
│   ├── dto/OnboardingRestauranteRequest/Response, ImportacionExcelRequest/Response
│   ├── entity/Restaurante.java
│   ├── mapper/OnboardingMapper.java
│   ├── repository/RestauranteRepository.java
│   └── service/OnboardingService.java
│
├── configuracion/
│   ├── controller/ConfiguracionController.java, MetodoPagoController.java
│   ├── dto/ConfiguracionRequest/Response, MetodoPagoRequest/Response
│   ├── entity/Configuracion.java, MetodoPago.java
│   ├── mapper/ConfiguracionMapper.java
│   ├── repository/ConfiguracionRepository.java, MetodoPagoRepository.java
│   └── service/ConfiguracionService.java, MetodoPagoService.java
│
├── lugar_atencion/
│   ├── controller/LugarAtencionController.java, QRLugarController.java
│   ├── dto/LugarAtencionRequest/Response, QRLugarRequest/Response
│   ├── entity/LugarAtencion.java, QRLugar.java
│   ├── mapper/LugarAtencionMapper.java
│   ├── repository/LugarAtencionRepository.java, QRLugarRepository.java
│   └── service/LugarAtencionService.java, QRLugarService.java, MapaLugarService.java
│
├── producto/
│   ├── controller/CategoriaProductoController.java, ProductoController.java
│   ├── dto/CategoriaProductoRequest/Response, ProductoRequest/Response
│   ├── entity/CategoriaProducto.java, Producto.java
│   ├── mapper/ProductoMapper.java
│   ├── repository/CategoriaProductoRepository.java, ProductoRepository.java
│   └── service/CategoriaProductoService.java, ProductoService.java
│
├── pedido/
│   ├── controller/PedidoController.java
│   ├── dto/PedidoRequest/Response, DetallePedidoRequest/Response, CambioEstadoRequest
│   ├── entity/Pedido.java, DetallePedido.java
│   ├── mapper/PedidoMapper.java
│   ├── repository/PedidoRepository.java, DetallePedidoRepository.java
│   └── service/PedidoService.java, DetallePedidoService.java, EstadoPedidoService.java
│
├── pago/
│   ├── controller/PagoController.java
│   ├── dto/PagoRequest/Response, ValidacionPagoRequest
│   ├── entity/Pago.java
│   ├── mapper/PagoMapper.java
│   ├── repository/PagoRepository.java
│   └── service/PagoService.java, ValidacionPagoService.java
│
├── reserva/
│   ├── controller/ReservaController.java
│   ├── dto/ReservaRequest/Response, ReservaPagoRequest/Response
│   ├── entity/Reserva.java, ReservaPago.java
│   ├── mapper/ReservaMapper.java
│   ├── repository/ReservaRepository.java, ReservaPagoRepository.java
│   └── service/ReservaService.java, ReservaPagoService.java, DisponibilidadReservaService.java
│
├── venta/
│   ├── controller/VentaController.java, DocVentaController.java
│   ├── dto/VentaRequest/Response, DocVentaRequest/Response
│   ├── entity/Venta.java, DocVenta.java
│   ├── mapper/VentaMapper.java
│   ├── repository/VentaRepository.java, DocVentaRepository.java
│   └── service/VentaService.java, DocVentaService.java
│
├── notificacion/
│   ├── controller/NotificacionController.java
│   ├── dto/NotificacionRequest/Response
│   ├── entity/Notificacion.java
│   ├── mapper/NotificacionMapper.java
│   ├── repository/NotificacionRepository.java
│   └── service/NotificacionService.java
│
├── cocina/
│   ├── controller/CocinaController.java
│   ├── dto/CocinaPedidoResponse, CocinaDetalleDTO, CambioEstadoCocinaRequest
│   ├── mapper/CocinaMapper.java
│   └── service/CocinaService.java, FlujoCocinaService.java
│
├── contabilidad/
│   ├── controller/ContabilidadController.java
│   ├── dto/VentaDiariaResponse, CierreCajaRequest/Response, ReporteContableResponse
│   ├── mapper/ContabilidadMapper.java
│   └── service/ContabilidadService.java
│
├── delivery/
│   ├── controller/DeliveryController.java
│   ├── dto/DeliveryRequest/Response, SeguimientoDeliveryResponse
│   └── service/DeliveryService.java, SeguimientoDeliveryService.java
│
├── facturacion/
│   ├── controller/FacturacionController.java
│   ├── dto/FacturaRequest/Response, ComprobanteResponse
│   └── service/FacturacionService.java, EmisionDocService.java
│
└── reportes/
    ├── controller/ReporteController.java
    ├── dto/ReporteVentasResponse, ReportePedidosResponse, ReporteReservasResponse,
    │        ReporteMesasResponse, ReporteEntregasResponse
    └── service/ReporteVentasService.java, ReportePedidosService.java,
             ReporteReservasService.java, ReporteEntregasService.java
```

---

## Endpoints completos del sistema

### Autenticación (Bloque 1)
| Método | Ruta | Acceso |
|--------|------|--------|
| POST | `/api/auth/login` | Público |
| POST | `/api/auth/register` | Público |
| POST | `/api/auth/logout` | Público |

### Onboarding (Bloque 1)
| Método | Ruta | Acceso |
|--------|------|--------|
| POST | `/api/onboarding` | Público |
| GET | `/api/onboarding/estado` | Público |
| GET | `/api/onboarding/restaurante` | Público |

### Producto (Bloque 2)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/categorias-producto` | Público |
| GET | `/api/categorias-producto/{id}` | Público |
| POST | `/api/categorias-producto` | ADMIN |
| PUT | `/api/categorias-producto/{id}` | ADMIN |
| DELETE | `/api/categorias-producto/{id}` | ADMIN |
| GET | `/api/productos` | Público |
| GET | `/api/productos/{id}` | Público |
| GET | `/api/productos/categoria/{idCategoria}` | Público |
| GET | `/api/productos/buscar/{nombre}` | Público |
| POST | `/api/productos` | ADMIN |
| PUT | `/api/productos/{id}` | ADMIN |
| PATCH | `/api/productos/{id}/stock` | ADMIN |
| DELETE | `/api/productos/{id}` | ADMIN |

### Pedido (Bloque 2)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/pedidos` | Authenticated |
| GET | `/api/pedidos/{id}` | Authenticated |
| GET | `/api/pedidos/usuario/{idUsuario}` | Authenticated |
| GET | `/api/pedidos/estado/{estado}` | Authenticated |
| POST | `/api/pedidos` | Authenticated |
| PUT | `/api/pedidos/{id}/estado` | ADMIN |
| PUT | `/api/pedidos/{id}/pagar` | ADMIN |
| DELETE | `/api/pedidos/{id}` | ADMIN |

### Pago (Bloque 2)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/pagos` | Authenticated |
| GET | `/api/pagos/{id}` | Authenticated |
| GET | `/api/pagos/codigo/{codigoPago}` | Authenticated |
| GET | `/api/pagos/estado/{estado}` | Authenticated |
| POST | `/api/pagos` | Authenticated |
| PUT | `/api/pagos/{id}/validar` | ADMIN |
| DELETE | `/api/pagos/{id}` | ADMIN |

### Reserva (Bloque 2)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/reservas` | Público |
| GET | `/api/reservas/{id}` | Público |
| GET | `/api/reservas/usuario/{idUsuario}` | Público |
| GET | `/api/reservas/lugar/{idLugar}` | Público |
| GET | `/api/reservas/disponibilidad` | Público |
| POST | `/api/reservas` | Authenticated |
| PUT | `/api/reservas/{id}/confirmar` | ADMIN |
| PUT | `/api/reservas/{id}/cancelar` | ADMIN |
| DELETE | `/api/reservas/{id}` | ADMIN |

### Venta (Bloque 2)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/ventas` | ADMIN |
| GET | `/api/ventas/{id}` | ADMIN |
| GET | `/api/ventas/pedido/{idPedido}` | ADMIN |
| POST | `/api/ventas` | ADMIN |
| PUT | `/api/ventas/{id}/cerrar` | ADMIN |
| PUT | `/api/ventas/{id}/anular` | ADMIN |

### Documentos de Venta (Bloque 2)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/documentos-venta` | ADMIN |
| GET | `/api/documentos-venta/{id}` | ADMIN |
| POST | `/api/documentos-venta` | ADMIN |

### Notificación (Bloque 2/3)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/notificaciones/usuario/{idUsuario}` | Authenticated |
| GET | `/api/notificaciones/usuario/{idUsuario}/no-leidas` | Authenticated |
| GET | `/api/notificaciones/usuario/{idUsuario}/contar` | Authenticated |
| POST | `/api/notificaciones` | Authenticated |
| PUT | `/api/notificaciones/{id}/leer` | Authenticated |
| DELETE | `/api/notificaciones/{id}` | Authenticated |

### Cocina (Bloque 2/3)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/cocina/pendientes` | ADMIN |
| GET | `/api/cocina/preparacion` | ADMIN |
| GET | `/api/cocina/listos` | ADMIN |
| PUT | `/api/cocina/{idPedido}/estado` | ADMIN |

### Contabilidad (Bloque 2/3)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/contabilidad/ventas-diarias` | ADMIN |
| GET | `/api/contabilidad/resumen` | ADMIN |

### Delivery (Bloque 2/3)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/delivery` | ADMIN |
| GET | `/api/delivery/seguimiento/{idPedido}` | ADMIN |
| POST | `/api/delivery/asignar` | ADMIN |
| PUT | `/api/delivery/{idPedido}/estado` | ADMIN |

### Facturación (Bloque 2/3)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/facturacion/comprobantes` | ADMIN |
| GET | `/api/facturacion/comprobantes/{id}` | ADMIN |
| POST | `/api/facturacion/emitir` | ADMIN |

### Reportes (Bloque 2/3)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/reportes/ventas` | ADMIN |
| GET | `/api/reportes/pedidos` | ADMIN |
| GET | `/api/reportes/reservas` | ADMIN |
| GET | `/api/reportes/entregas` | ADMIN |

### Configuración (Bloque 1)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/configuracion` | ADMIN |
| GET | `/api/configuracion/{clave}` | ADMIN |
| POST | `/api/configuracion` | ADMIN |
| PUT | `/api/configuracion/{id}` | ADMIN |
| DELETE | `/api/configuracion/{id}` | ADMIN |

### Métodos de Pago (Bloque 1)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/metodos-pago` | Público |
| GET | `/api/metodos-pago/activos` | Público |
| GET | `/api/metodos-pago/{id}` | Público |
| POST | `/api/metodos-pago` | ADMIN |
| PUT | `/api/metodos-pago/{id}` | ADMIN |
| DELETE | `/api/metodos-pago/{id}` | ADMIN |

### Lugares de Atención (Bloque 1)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/lugares` | Público |
| GET | `/api/lugares/activos` | Público |
| GET | `/api/lugares/{id}` | Público |
| GET | `/api/lugares/tipo/{tipo}` | Público |
| POST | `/api/lugares` | ADMIN |
| PUT | `/api/lugares/{id}` | ADMIN |
| DELETE | `/api/lugares/{id}` | ADMIN |

### QR (Bloque 1)
| Método | Ruta | Acceso |
|--------|------|--------|
| POST | `/api/qr/generar` | ADMIN |
| GET | `/api/qr/lugar/{idLugar}` | Público |
| GET | `/api/qr/validar/{codigoQR}` | Público |
| PUT | `/api/qr/desactivar/{idQR}` | ADMIN |

### Usuarios (Bloque 1)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/usuarios` | ADMIN |
| GET | `/api/usuarios/{id}` | ADMIN |
| POST | `/api/usuarios` | ADMIN |
| PUT | `/api/usuarios/{id}` | ADMIN |
| DELETE | `/api/usuarios/{id}` | ADMIN |

### Roles (Bloque 1)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/roles` | Público |
| GET | `/api/roles/{id}` | Público |
| POST | `/api/roles` | ADMIN |
| PUT | `/api/roles/{id}` | ADMIN |
| DELETE | `/api/roles/{id}` | ADMIN |

---

## Flujo funcional completo del sistema

```
Registro/Login (auth)
    │
    ▼
Onboarding (crear restaurante)
    │
    ▼
Configuración (moneda, impuestos, horarios, métodos de pago)
    │
    ▼
Gestión de Lugares (mesas, salones, terraza, recojo, despacho)
    │
    ▼
Gestión de Productos (categorías + productos con precio/stock)
    │
    ├──→ Cliente hace Pedido (local/delivery/recojo)
    │       │
    │       ├──→ Cocina recibe pedido (pendiente → en_preparacion → listo)
    │       │
    │       ├──→ Delivery asignado (en_camino → entregado)
    │       │
    │       └──→ Pago registrado (pendiente → aprobado/rechazado)
    │               │
    │               ├──→ Venta generada → Documento (boleta/factura/ticket)
    │               │
    │               └──→ Notificaciones al usuario
    │
    ├──→ Cliente hace Reserva
    │       │
    │       ├──→ Verificar disponibilidad
    │       ├──→ Confirmar con adelanto (ReservaPago)
    │       └──→ Cancelar con penalidad
    │
    └──→ Reportes (ventas, pedidos, reservas, entregas)
    
    └──→ Contabilidad (ventas diarias, cierres, resúmenes)
```

---

## Tablas de base de datos creadas

| # | Tabla | Módulo |
|---|-------|--------|
| 1 | Rol | Existente |
| 2 | Usuario | Existente |
| 3 | Restaurante | onboarding |
| 4 | LugarAtencion | lugar_atencion |
| 5 | QRLugar | lugar_atencion |
| 6 | MetodoPago | configuracion |
| 7 | Configuracion | configuracion |
| 8 | CategoriaProducto | producto |
| 9 | Producto | producto |
| 10 | Pedido | pedido |
| 11 | DetallePedido | pedido |
| 12 | Pago | pago |
| 13 | Reserva | reserva |
| 14 | ReservaPago | reserva |
| 15 | Venta | venta |
| 16 | DocVenta | venta |
| 17 | Notificacion | notificacion |

---

## Resumen final

| Concepto | Cantidad |
|----------|----------|
| Módulos backend | 17 |
| Archivos Java creados | ~146 |
| Entidades JPA | 17 |
| Repositorios | 17 |
| Servicios | 29 |
| Controladores | 19 |
| DTOs | 36 |
| Mappers | 10 |
| Endpoints API | ~90 |

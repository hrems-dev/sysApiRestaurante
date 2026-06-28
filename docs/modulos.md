# Clasificación por módulos y por carpetas

## 1. Estructura general del backend

```text
src/main/java/com/system/restaurante/
├── config/
├── security/
├── exception/
├── util/
├── modules/
└── common/
```

---

## 2. Carpeta common

```text
common/
├── constants/
├── enums/
├── response/
├── request/
└── base/
```

### Contenido
- `constants/`: valores globales del sistema.
- `enums/`: estados y tipos comunes.
- `response/`: respuestas genéricas API.
- `request/`: modelos base de entrada.
- `base/`: clases base comunes como auditoría.

---

## 3. Carpeta modules

```text
modules/
├── autenticacion/
├── onboarding/
├── usuario/
├── lugar_atencion/
├── reserva/
├── pago/
├── pedido/
├── producto/
├── venta/
├── notificacion/
├── cocina/
├── contabilidad/
├── delivery/
├── facturacion/
└── reportes/
```

---

## 4. Estructura de cada módulo

Cada módulo debe contener esta misma lógica de capas:

```text
modulo/
├── controller/
├── dto/
├── entity/
├── mapper/
├── repository/
└── service/
```

Si el módulo es más complejo, se pueden agregar:

```text
modulo/
├── controller/
├── dto/
├── entity/
├── mapper/
├── repository/
├── service/
├── usecase/
├── validator/
└── specification/
```

---

## 5. Módulo de autenticación

```text
modules/autenticacion/
├── controller/
│   ├── AuthController.java
│   ├── RegistroController.java
│   └── SesionController.java
├── dto/
│   ├── LoginRequest.java
│   ├── RegistroRequest.java
│   ├── AuthResponse.java
│   └── RefreshTokenRequest.java
├── entity/
│   ├── Usuario.java
│   └── Rol.java
├── mapper/
│   └── AuthMapper.java
├── repository/
│   ├── UsuarioRepository.java
│   └── RolRepository.java
└── service/
    ├── AuthService.java
    ├── LoginService.java
    └── RegistroService.java
```

---

## 6. Módulo de onboarding

```text
modules/onboarding/
├── controller/
│   └── OnboardingController.java
├── dto/
│   ├── OnboardingRestauranteRequest.java
│   ├── OnboardingRestauranteResponse.java
│   ├── ImportacionExcelRequest.java
│   └── ImportacionExcelResponse.java
├── entity/
│   ├── Restaurante.java
│   ├── LugarAtencion.java
│   ├── QRLugar.java
│   └── MetodoPago.java
├── mapper/
│   └── OnboardingMapper.java
├── repository/
│   ├── RestauranteRepository.java
│   ├── LugarAtencionRepository.java
│   ├── QRLugarRepository.java
│   └── MetodoPagoRepository.java
└── service/
    ├── OnboardingService.java
    ├── ExcelImportService.java
    └── PlantillaExcelService.java
```

---

## 7. Módulo de usuario

```text
modules/usuario/
├── controller/
│   └── UsuarioController.java
├── dto/
│   ├── UsuarioRequest.java
│   ├── UsuarioResponse.java
│   ├── RolRequest.java
│   └── RolResponse.java
├── entity/
│   ├── Usuario.java
│   └── Rol.java
├── mapper/
│   └── UsuarioMapper.java
├── repository/
│   ├── UsuarioRepository.java
│   └── RolRepository.java
└── service/
    ├── UsuarioService.java
    └── RolService.java
```

---

## 8. Módulo de lugar de atención

```text
modules/lugar_atencion/
├── controller/
│   ├── LugarAtencionController.java
│   └── QRLugarController.java
├── dto/
│   ├── LugarAtencionRequest.java
│   ├── LugarAtencionResponse.java
│   ├── QRLugarRequest.java
│   └── QRLugarResponse.java
├── entity/
│   ├── LugarAtencion.java
│   └── QRLugar.java
├── mapper/
│   └── LugarAtencionMapper.java
├── repository/
│   ├── LugarAtencionRepository.java
│   └── QRLugarRepository.java
└── service/
    ├── LugarAtencionService.java
    ├── QRLugarService.java
    └── MapaLugarService.java
```

---

## 9. Módulo de reserva

```text
modules/reserva/
├── controller/
│   └── ReservaController.java
├── dto/
│   ├── ReservaRequest.java
│   ├── ReservaResponse.java
│   ├── ReservaPagoRequest.java
│   └── ReservaPagoResponse.java
├── entity/
│   ├── Reserva.java
│   └── ReservaPago.java
├── mapper/
│   └── ReservaMapper.java
├── repository/
│   ├── ReservaRepository.java
│   └── ReservaPagoRepository.java
└── service/
    ├── ReservaService.java
    ├── ReservaPagoService.java
    └── DisponibilidadReservaService.java
```

---

## 10. Módulo de pago

```text
modules/pago/
├── controller/
│   └── PagoController.java
├── dto/
│   ├── PagoRequest.java
│   ├── PagoResponse.java
│   ├── ValidacionPagoRequest.java
│   └── MetodoPagoResponse.java
├── entity/
│   ├── Pago.java
│   └── MetodoPago.java
├── mapper/
│   └── PagoMapper.java
├── repository/
│   ├── PagoRepository.java
│   └── MetodoPagoRepository.java
└── service/
    ├── PagoService.java
    ├── MetodoPagoService.java
    └── ValidacionPagoService.java
```

---

## 11. Módulo de pedido

```text
modules/pedido/
├── controller/
│   └── PedidoController.java
├── dto/
│   ├── PedidoRequest.java
│   ├── PedidoResponse.java
│   ├── DetallePedidoRequest.java
│   └── DetallePedidoResponse.java
├── entity/
│   ├── Pedido.java
│   └── DetallePedido.java
├── mapper/
│   └── PedidoMapper.java
├── repository/
│   ├── PedidoRepository.java
│   └── DetallePedidoRepository.java
└── service/
    ├── PedidoService.java
    ├── DetallePedidoService.java
    └── EstadoPedidoService.java
```

---

## 12. Módulo de producto

```text
modules/producto/
├── controller/
│   ├── ProductoController.java
│   └── CategoriaProductoController.java
├── dto/
│   ├── ProductoRequest.java
│   ├── ProductoResponse.java
│   ├── CategoriaProductoRequest.java
│   └── CategoriaProductoResponse.java
├── entity/
│   ├── Producto.java
│   └── CategoriaProducto.java
├── mapper/
│   └── ProductoMapper.java
├── repository/
│   ├── ProductoRepository.java
│   └── CategoriaProductoRepository.java
└── service/
    ├── ProductoService.java
    └── CategoriaProductoService.java
```

---

## 13. Módulo de venta

```text
modules/venta/
├── controller/
│   ├── VentaController.java
│   └── DocVentaController.java
├── dto/
│   ├── VentaResponse.java
│   ├── DocVentaRequest.java
│   └── DocVentaResponse.java
├── entity/
│   ├── Venta.java
│   └── DocVenta.java
├── mapper/
│   └── VentaMapper.java
├── repository/
│   ├── VentaRepository.java
│   └── DocVentaRepository.java
└── service/
    ├── VentaService.java
    ├── DocVentaService.java
    └── CierreVentaService.java
```

---

## 14. Módulo de notificación

```text
modules/notificacion/
├── controller/
│   └── NotificacionController.java
├── dto/
│   ├── NotificacionRequest.java
│   └── NotificacionResponse.java
├── entity/
│   └── Notificacion.java
├── mapper/
│   └── NotificacionMapper.java
├── repository/
│   └── NotificacionRepository.java
└── service/
    ├── NotificacionService.java
    └── EnvioNotificacionService.java
```

---

## 15. Módulo de cocina

```text
modules/cocina/
├── controller/
│   └── CocinaController.java
├── dto/
│   ├── CocinaPedidoResponse.java
│   └── CambioEstadoCocinaRequest.java
├── entity/
│   └── Pedido.java
├── mapper/
│   └── CocinaMapper.java
├── repository/
│   └── PedidoRepository.java
└── service/
    ├── CocinaService.java
    └── FlujoCocinaService.java
```

---

## 16. Módulo de contabilidad

```text
modules/contabilidad/
├── controller/
│   └── ContabilidadController.java
├── dto/
│   ├── VentaDiariaResponse.java
│   ├── CierreCajaRequest.java
│   ├── CierreCajaResponse.java
│   └── ReporteContableResponse.java
├── entity/
│   ├── Pago.java
│   ├── Venta.java
│   └── CierreCaja.java
├── mapper/
│   └── ContabilidadMapper.java
├── repository/
│   ├── PagoRepository.java
│   ├── VentaRepository.java
│   └── CierreCajaRepository.java
└── service/
    ├── ContabilidadService.java
    ├── CajaCierreService.java
    └── ReporteContableService.java
```

---

## 17. Módulo de delivery

```text
modules/delivery/
├── controller/
│   └── DeliveryController.java
├── dto/
│   ├── DeliveryRequest.java
│   ├── DeliveryResponse.java
│   └── SeguimientoDeliveryResponse.java
├── entity/
│   └── Delivery.java
├── mapper/
│   └── DeliveryMapper.java
├── repository/
│   └── DeliveryRepository.java
└── service/
    ├── DeliveryService.java
    └── SeguimientoDeliveryService.java
```

---

## 18. Módulo de facturación

```text
modules/facturacion/
├── controller/
│   └── FacturacionController.java
├── dto/
│   ├── FacturaRequest.java
│   ├── FacturaResponse.java
│   └── ComprobanteResponse.java
├── entity/
│   └── DocVenta.java
├── mapper/
│   └── FacturacionMapper.java
├── repository/
│   └── DocVentaRepository.java
└── service/
    ├── FacturacionService.java
    └── EmisionDocService.java
```

---

## 19. Módulo de reportes

```text
modules/reportes/
├── controller/
│   └── ReporteController.java
├── dto/
│   ├── ReporteVentasResponse.java
│   ├── ReportePedidosResponse.java
│   ├── ReporteReservasResponse.java
│   ├── ReporteMesasResponse.java
│   └── ReporteEntregasResponse.java
├── entity/
│   └── ReporteResumen.java
├── mapper/
│   └── ReporteMapper.java
├── repository/
│   └── ReporteRepository.java
└── service/
    ├── ReporteVentasService.java
    ├── ReportePedidosService.java
    ├── ReporteReservasService.java
    └── ReporteEntregasService.java
```

---

## 20. Carpeta security

```text
security/
├── JwtService.java
├── JwtAuthenticationFilter.java
├── SecurityConfig.java
├── CustomUserDetailsService.java
└── UserPrincipal.java
```

---

## 21. Carpeta config

```text
config/
├── CorsConfig.java
├── JpaConfig.java
├── DataInitializer.java
├── SwaggerConfig.java
└── WebConfig.java
```

---

## 22. Carpeta exception

```text
exception/
├── ApiExceptionHandler.java
├── NegocioException.java
├── RecursoNoEncontradoException.java
└── ValidacionException.java
```

---

## 23. Carpeta util

```text
util/
├── FechasUtil.java
├── ExcelUtil.java
├── QrUtil.java
├── NumerosUtil.java
└── Constantes.java
```

---

## 24. Frontend por módulos

```text
src/app/features/
├── autenticacion/
├── onboarding-restaurante/
├── panel-cliente/
├── panel-mesero/
├── panel-cocina/
├── panel-contabilidad/
├── mesas-qr/
├── pedidos/
├── pagos/
├── reservas/
├── delivery/
├── facturacion/
├── reportes/
└── configuracion/
```

---

## 25. Frontend por capas

```text
core/
├── interceptors/
├── guards/
├── services/
├── models/
└── constants/

shared/
├── components/
├── pipes/
├── directives/
└── validators/

layout/
├── auth-layout/
├── main-layout/
├── header/
├── sidebar/
└── footer/
```

---

## 26. Regla final

```text
Cada módulo debe contener solo lo que usa.
Cada capa debe cumplir una sola responsabilidad.
Cada entidad de la base de datos debe vivir en su módulo funcional.
```

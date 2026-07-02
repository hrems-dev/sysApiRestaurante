# Bloque 1 - Completo

## Resumen de módulos implementados

| Módulo | Archivos creados | Estado |
|--------|-----------------|--------|
| autenticacion | 3 | ✅ Completo (login, register, logout, JWT) |
| onboarding | 9 | ✅ Completo (restaurante, estado onboarding) |
| configuracion | 13 | ✅ Completo (parámetros, métodos de pago) |
| usuario | 2 (refactor) | ✅ Completo (CRUD usuarios y roles) |
| lugar_atencion | 14 | ✅ Completo (lugares, QR, mapa) |

---

## Estructura modular creada

```
src/main/java/pe/edu/upeu/sys_api_restaurant/modules/
├── autenticacion/
│   ├── controller/
│   │   └── SesionController.java      (logout)
│   ├── dto/
│   │   ├── LogoutRequest.java
│   │   └── RefreshTokenRequest.java
│   └── service/
│       └── AuthService.java           (gestión de sesión)
│
├── onboarding/
│   ├── controller/
│   │   └── OnboardingController.java  (POST /api/onboarding, GET estado)
│   ├── dto/
│   │   ├── OnboardingRestauranteRequest.java
│   │   ├── OnboardingRestauranteResponse.java
│   │   ├── ImportacionExcelRequest.java
│   │   └── ImportacionExcelResponse.java
│   ├── entity/
│   │   └── Restaurante.java
│   ├── mapper/
│   │   └── OnboardingMapper.java
│   ├── repository/
│   │   └── RestauranteRepository.java
│   └── service/
│       └── OnboardingService.java
│
├── configuracion/
│   ├── controller/
│   │   ├── ConfiguracionController.java
│   │   └── MetodoPagoController.java
│   ├── dto/
│   │   ├── ConfiguracionRequest.java
│   │   ├── ConfiguracionResponse.java
│   │   ├── MetodoPagoRequest.java
│   │   └── MetodoPagoResponse.java
│   ├── entity/
│   │   ├── Configuracion.java
│   │   └── MetodoPago.java
│   ├── mapper/
│   │   └── ConfiguracionMapper.java
│   ├── repository/
│   │   ├── ConfiguracionRepository.java
│   │   └── MetodoPagoRepository.java
│   └── service/
│       ├── ConfiguracionService.java
│       └── MetodoPagoService.java
│
├── lugar_atencion/
│   ├── controller/
│   │   ├── LugarAtencionController.java
│   │   └── QRLugarController.java
│   ├── dto/
│   │   ├── LugarAtencionRequest.java
│   │   ├── LugarAtencionResponse.java
│   │   ├── QRLugarRequest.java
│   │   └── QRLugarResponse.java
│   ├── entity/
│   │   ├── LugarAtencion.java
│   │   └── QRLugar.java
│   ├── mapper/
│   │   └── LugarAtencionMapper.java
│   ├── repository/
│   │   ├── LugarAtencionRepository.java
│   │   └── QRLugarRepository.java
│   └── service/
│       ├── LugarAtencionService.java
│       ├── QRLugarService.java
│       └── MapaLugarService.java
```

---

## Archivos existentes actualizados

| Archivo | Cambio |
|---------|--------|
| `security/SecurityConfig.java` | Nuevas rutas para onboarding, lugares, QR, metodos-pago, configuracion |
| `config/DataInitializer.java` | Seed de MetodoPago (6 métodos) y Configuracion (5 parámetros) |

---

## Endpoints disponibles

### Autenticación
| Método | Ruta | Acceso |
|--------|------|--------|
| POST | `/api/auth/login` | Público |
| POST | `/api/auth/register` | Público |
| POST | `/api/auth/logout` | Público |

### Onboarding
| Método | Ruta | Acceso |
|--------|------|--------|
| POST | `/api/onboarding` | Público |
| GET | `/api/onboarding/estado` | Público |
| GET | `/api/onboarding/restaurante` | Público |

### Lugares de atención
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/lugares` | Público |
| GET | `/api/lugares/activos` | Público |
| GET | `/api/lugares/{id}` | Público |
| GET | `/api/lugares/tipo/{tipo}` | Público |
| POST | `/api/lugares` | ADMIN |
| PUT | `/api/lugares/{id}` | ADMIN |
| DELETE | `/api/lugares/{id}` | ADMIN |

### Códigos QR
| Método | Ruta | Acceso |
|--------|------|--------|
| POST | `/api/qr/generar` | ADMIN |
| GET | `/api/qr/lugar/{idLugar}` | Público |
| GET | `/api/qr/validar/{codigoQR}` | Público |
| PUT | `/api/qr/desactivar/{idQR}` | ADMIN |

### Configuración
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/configuracion` | ADMIN |
| GET | `/api/configuracion/{clave}` | ADMIN |
| POST | `/api/configuracion` | ADMIN |
| PUT | `/api/configuracion/{id}` | ADMIN |
| DELETE | `/api/configuracion/{id}` | ADMIN |

### Métodos de pago
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/metodos-pago` | Público |
| GET | `/api/metodos-pago/activos` | Público |
| GET | `/api/metodos-pago/{id}` | Público |
| POST | `/api/metodos-pago` | ADMIN |
| PUT | `/api/metodos-pago/{id}` | ADMIN |
| DELETE | `/api/metodos-pago/{id}` | ADMIN |

### Usuarios (existente)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/usuarios` | ADMIN |
| GET | `/api/usuarios/{id}` | ADMIN |
| POST | `/api/usuarios` | ADMIN |
| PUT | `/api/usuarios/{id}` | ADMIN |
| DELETE | `/api/usuarios/{id}` | ADMIN |

### Roles (existente)
| Método | Ruta | Acceso |
|--------|------|--------|
| GET | `/api/roles` | Público |
| GET | `/api/roles/{id}` | Público |
| POST | `/api/roles` | ADMIN |
| PUT | `/api/roles/{id}` | ADMIN |
| DELETE | `/api/roles/{id}` | ADMIN |

---

## Datos semilla (creados al iniciar)

### Métodos de pago
- EFECTIVO
- TARJETA_CREDITO
- TARJETA_DEBITO
- YAPE
- PLIN
- TRANSFERENCIA

### Configuración
| Clave | Valor |
|-------|-------|
| MONEDA | PEN |
| IGV | 18 |
| HORA_APERTURA | 08:00 |
| HORA_CIERRE | 23:00 |
| NOMBRE_RESTAURANTE | Mi Restaurante |

---

## Total: ~40 archivos nuevos + 2 modificados

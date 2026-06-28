# Bloque 1: módulos necesarios

## 1. Autenticación

### Carpeta

```text
modules/autenticacion/
```

### Necesario

- Entidad `Usuario`.
- Entidad `Rol`.
- DTOs de login, registro y respuesta JWT.
- Repositorios de usuario y rol.
- Servicio de autenticación.
- Controlador de login, registro y cierre de sesión.
- Seguridad JWT.
- Filtro de autenticación.
- Guard en frontend.
- Interceptor JWT en frontend.
- Manejo de sesión almacenada.
- Protección de rutas.

### Tablas relacionadas

- `Rol`
- `Usuario`

---

## 2. Onboarding restaurante

### Carpeta

```text
modules/onboarding/
```

### Necesario

- Entidad principal de restaurante si aún no existe.
- DTO de registro inicial del restaurante.
- DTO para carga manual.
- DTO para importación Excel.
- DTO de resultado de importación.
- Servicio para crear restaurante por primera vez.
- Servicio para validar si ya existe configuración inicial.
- Servicio para importar datos masivos.
- Servicio para generar plantilla Excel.
- Controlador de onboarding.
- Validación para bloquear operación si no existe restaurante.

### Tablas relacionadas

- `Restaurante`
- `LugarAtencion`
- `QRLugar`
- `MetodoPago`
- `Usuario`
- `Rol`

---

## 3. Configuración

### Carpeta

```text
modules/configuracion/
```

### Necesario

- DTO de configuración general.
- Servicio para parámetros del sistema.
- Servicio para moneda, impuestos y horarios.
- Servicio para métodos de pago permitidos.
- Repositorio o tabla de parámetros globales.
- Controlador de configuración.
- Frontend de ajustes iniciales.

### Tablas relacionadas

- `Restaurante`
- `MetodoPago`
- `Rol` si manejas permisos globales

---

## 4. Usuario

### Carpeta

```text
modules/usuario/
```

### Necesario

- Entidad `Usuario`.
- Entidad `Rol`.
- DTO de usuario.
- DTO de rol.
- Repositorios.
- Servicio para CRUD de usuarios.
- Servicio para asignación de roles.
- Controlador de usuarios.
- Frontend de administración de usuarios.

### Tablas relacionadas

- `Usuario`
- `Rol`

---

## 5. Lugar de atención

### Carpeta

```text
modules/lugar_atencion/
```

### Necesario

- Entidad `LugarAtencion`.
- Entidad `QRLugar`.
- DTO de lugar.
- DTO de QR.
- Repositorios.
- Servicio para crear, actualizar y desactivar lugares.
- Servicio para generar QR.
- Servicio para validar QR.
- Controlador del módulo.
- Frontend de mesas, salones, terraza, recojo y despacho.

### Tablas relacionadas

- `LugarAtencion`
- `QRLugar`

---

## 6. Base compartida del frontend

### Carpetas

```text
src/app/core/
src/app/shared/
src/app/layout/
```

### Necesario

- Interceptor JWT.
- Guard de autenticación.
- Guard de onboarding.
- Servicios base para consumir API.
- Modelos base.
- Componentes reutilizables.
- Layout principal y layout de autenticación.

---

# Orden de construcción dentro del bloque 1

## Paso 1

```text
Rol
Usuario
```

## Paso 2

```text
LugarAtencion
QRLugar
```

## Paso 3

```text
Onboarding restaurante
Configurar restaurante base
```

## Paso 4

```text
Autenticación JWT
```

## Paso 5

```text
Configuración general
```

---

# Qué debe salir del bloque 1

## Backend

```text
- Entidades base
- DTOs base
- Repositorios base
- Servicios base
- Controladores base
- Seguridad JWT
- Manejo de errores
- Configuración inicial
```

## Frontend

```text
- Login
- Registro
- Guard de autenticación
- Interceptor JWT
- Onboarding restaurante
- Configuración general
- Panel de administración base
```

---

# Dependencias del bloque 1

```text
Sin bloque 1 no se puede construir correctamente:
- pedido
- pago
- reserva
- venta
- cocina
- contabilidad
- delivery
- facturación
- reportes
```

---

# Resumen de lo indispensable

```text
NECESARIO PARA BLOQUE 1:
- Rol
- Usuario
- LugarAtencion
- QRLugar
- Restaurante
- MetodoPago
- Configuración
- Seguridad JWT
- Onboarding
- Interceptor
- Guard
```

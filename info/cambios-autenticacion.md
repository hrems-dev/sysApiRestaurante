# Cambios realizados - Autenticación

## 1. Creación de usuario admin por defecto

**Archivo:** `src/main/java/pe/edu/upeu/sys_api_restaurant/config/DataInitializer.java`

- Inyectados `UsuarioRepository` y `PasswordEncoder`
- Al iniciar, si no hay usuarios en la BD, se crea un usuario `admin` con contraseña `admin123` y rol `ADMIN`
- Anteriormente solo se creaba el rol ADMIN, pero no había un usuario para iniciar sesión

---

## 2. Corrección de campo de error en frontend

**Archivos:**
- `frontend/apprestaurant/src/app/features/auth/pages/login/login.ts`
- `frontend/apprestaurant/src/app/features/auth/pages/register/register.ts`

**Cambio:** `err?.error?.message` → `err?.error?.mensaje`

El backend envía el error en el campo `mensaje` (español), pero el frontend intentaba leer `message` (inglés), por lo que los errores reales nunca se mostraban al usuario.

---

## 3. Interceptor global para 401 (token expirado)

**Archivo nuevo:** `frontend/apprestaurant/src/app/core/interceptors/error-interceptor.ts`

- Atrapa todas las respuestas HTTP con código 401
- Limpia `localStorage` (token, username, rol)
- Redirige automáticamente a `/auth/login`

**Archivo modificado:** `frontend/apprestaurant/src/app/app.config.ts`

- Registrado `errorInterceptor` junto al `jwtInterceptor`

---

## 4. Codificación de contraseñas en CRUD de usuarios

**Archivo:** `src/main/java/pe/edu/upeu/sys_api_restaurant/service/UsuarioService.java`

- Inyectado `PasswordEncoder`
- `save()`: ahora codifica la contraseña con BCrypt antes de guardar
- `update()`: si la nueva contraseña es texto plano (no empieza con `$2`), la codifica; si ya es un hash BCrypt, la mantiene igual
- Anteriormente las contraseñas se guardaban en texto plano cuando se usaban los endpoints CRUD (`/api/usuarios`)

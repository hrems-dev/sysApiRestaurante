# Flujo de Autenticación y Autorización

## Archivos creados (12)

| # | Archivo | Ubicación |
|---|---------|-----------|
| 1 | `CustomUserDetailsService` | `security/CustomUserDetailsService.java` |
| 2 | `JwtService` | `security/JwtService.java` |
| 3 | `JwtAuthFilter` | `security/JwtAuthFilter.java` |
| 4 | `SecurityConfig` | `security/SecurityConfig.java` |
| 5 | `AuthController` | `controller/AuthController.java` |
| 6 | `LoginRequest` | `dto/LoginRequest.java` |
| 7 | `RegisterRequest` | `dto/RegisterRequest.java` |
| 8 | `AuthResponse` | `dto/AuthResponse.java` |
| 9 | `GlobalExceptionHandler` | `exception/GlobalExceptionHandler.java` |
| 10 | `BadRequestException` | `exception/BadRequestException.java` |
| 11 | `PasswordEncoder bean` | `security/SecurityConfig.java` — `BCryptPasswordEncoder` |
| 12 | `AuthenticationManager bean` | `security/SecurityConfig.java` — desde `AuthenticationConfiguration` |

---

## Flujo: Registro

```
POST /api/auth/register
Body: { username, nombres, apellidos, email, password, telefono, rolNombre }
         │
         ▼
  AuthController.register()
         │
         ├── Valida @Valid (username no vacío, password ≥ 6 chars, email válido...)
         │
         ├── ¿username duplicado?  → BadRequestException → 400
         ├── ¿email duplicado?     → BadRequestException → 400
         │
         ├── Busca Rol por nombre  → ¿no existe? → BadRequestException → 400
         │
         ├── passwordEncoder.encode(password) → BCrypt hash
         │
         ├── Construye Usuario (estadoUsuario = true, fechaCreacion = now)
         │
         ├── usuarioRepository.save(usuario) → PostgreSQL
         │
         ├── jwtService.generateToken(username, rol) → JWT firmado con HMAC-SHA
         │
         └── 201 Created + AuthResponse { token, username, rol, mensaje }
```

## Flujo: Login

```
POST /api/auth/login
Body: { username, password }
         │
         ▼
  AuthController.login()
         │
         ├── authenticationManager.authenticate(
         │       UsernamePasswordAuthenticationToken(username, password) )
         │         │
         │         ▼
         │   CustomUserDetailsService.loadUserByUsername(username)
         │         │
         │         ├── usuarioRepository.findByNombreUsuario(username)
         │         │     └── ¿no existe? → UsernameNotFoundException → 401
         │         │
         │         └── Devuelve Usuario (implementa UserDetails)
         │               ├── getPassword()    → passwordHash
         │               ├── getAuthorities() → [ROLE_ADMIN]
         │               └── isEnabled()      → estadoUsuario
         │         │
         │         ▼
         │   BCryptPasswordEncoder.matches(password, passwordHash) → ¿coincide?
         │         └── ¿no coincide? → BadCredentialsException → 401
         │
         ├── jwtService.generateToken(username, rol.nombreRol)
         │     ├── subject = username
         │     ├── claim "rol" = nombreRol
         │     ├── issuedAt = ahora
         │     ├── expiration = ahora + 86400000ms (24h)
         │     └── signedWith = clave HMAC-SHA (jwt.secret)
         │
         └── 200 OK + AuthResponse { token, username, rol, mensaje }
```

## Flujo: Request protegida con JWT

```
GET /api/usuarios
Header: Authorization: Bearer <token>
         │
         ▼
  SecurityFilterChain
         │
         ├── JwtAuthFilter (antes de UsernamePasswordAuthenticationFilter)
         │     │
         │     ├── Lee header "Authorization"
         │     ├── ¿null o no empieza con "Bearer "? → continua filterChain
         │     │
         │     ├── Extrae token (substring después de "Bearer ")
         │     │
         │     ├── jwtService.isValid(token)
         │     │     ├── parseSignedClaims → verifica firma HMAC
         │     │     ├── verifica expiración → ¿expirado? → false
         │     │     └── ¿token inválido? → continua filterChain (401 eventual)
         │     │
         │     ├── jwtService.extractUsername(token) → getSubject()
         │     │
         │     ├── CustomUserDetailsService.loadUserByUsername(username)
         │     │
         │     ├── Crea UsernamePasswordAuthenticationToken(
         │     │       userDetails, null, userDetails.getAuthorities() )
         │     │
         │     ├── SecurityContextHolder.getContext().setAuthentication(auth)
         │     │
         │     └── filterChain.doFilter(request, response)
         │
         ├── AuthorizeHttpRequests
         │     ├── /api/auth/**              → permitAll()
         │     ├── GET /api/roles/**          → permitAll()
         │     ├── /api/roles/**             → hasRole("ADMIN")
         │     ├── /api/usuarios/**          → hasRole("ADMIN")
         │     └── cualquier otra ruta       → authenticated()
         │
         └── ¿autorizado? → 200 OK
             └── ¿no autorizado? → 403 Forbidden
```

## Dependencias entre archivos

```
AuthController
  ├── AuthenticationManager          (inyectado por Spring)
  ├── UsuarioRepository              → busca/guarda usuarios
  ├── RolRepository                  → busca rol por nombre
  ├── PasswordEncoder                → BCryptPasswordEncoder
  └── JwtService                     → genera JWT

CustomUserDetailsService
  └── UsuarioRepository              → loadUserByUsername()

JwtAuthFilter
  ├── JwtService                     → valida y extrae username del token
  └── CustomUserDetailsService       → carga UserDetails

SecurityConfig
  ├── JwtAuthFilter                  → registrado en la cadena de filtros
  ├── PasswordEncoder                → bean BCryptPasswordEncoder
  └── AuthenticationManager          → bean desde AuthenticationConfiguration
```

## Validaciones aplicadas

| Campo | Validación |
|-------|-----------|
| `username` | `@NotBlank`, `@Size(min=3, max=50)` |
| `password` | `@NotBlank`, `@Size(min=6)` |
| `email` | `@NotBlank`, `@Email` |
| `nombres` | `@NotBlank`, `@Size(min=6, max=80)` |
| `apellidos` | `@NotBlank`, `@Size(min=6, max=80)` |
| `rolNombre` | `@NotBlank` |
| Username duplicado | Validación manual en `register()` |
| Email duplicado | Validación manual en `register()` |
| Contraseña | Siempre encriptada con `BCryptPasswordEncoder` |
| Usuario sin rol | `@ManyToOne` con `nullable = false` + validación manual |

package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.AuthResponse;
import pe.edu.upeu.sys_api_restaurant.dto.LoginRequest;
import pe.edu.upeu.sys_api_restaurant.dto.RegisterRequest;
import pe.edu.upeu.sys_api_restaurant.entity.Rol;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.exception.BadRequestException;
import pe.edu.upeu.sys_api_restaurant.repository.RolRepository;
import pe.edu.upeu.sys_api_restaurant.repository.UsuarioRepository;
import pe.edu.upeu.sys_api_restaurant.security.JwtService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(), request.password()));

        Usuario usuario = usuarioRepository.findByNombreUsuario(request.username())
                .orElseThrow();

        String token = jwtService.generateToken(
                usuario.getUsername(), usuario.getRol().getNombreRol());

        return ResponseEntity.ok(new AuthResponse(
                token, usuario.getUsername(),
                usuario.getRol().getNombreRol(), "Inicio de sesión exitoso"));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        if (usuarioRepository.findByNombreUsuario(request.username()).isPresent()) {
            throw new BadRequestException("El username ya está registrado");
        }
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new BadRequestException("El email ya está registrado");
        }

        Rol rol = rolRepository.findByNombreRol(request.rolNombre())
                .orElseThrow(() -> new BadRequestException(
                        "Rol no encontrado: " + request.rolNombre()));

        Usuario usuario = Usuario.builder()
                .nombreUsuario(request.username())
                .nombres(request.nombres())
                .apellidos(request.apellidos())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .telefono(request.telefono())
                .estadoUsuario(true)
                .rol(rol)
                .build();

        usuario = usuarioRepository.save(usuario);

        String token = jwtService.generateToken(
                usuario.getUsername(), usuario.getRol().getNombreRol());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new AuthResponse(token, usuario.getUsername(),
                        usuario.getRol().getNombreRol(), "Usuario registrado exitosamente"));
    }
}

package pe.edu.upeu.sys_api_restaurant.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.entity.*;
import pe.edu.upeu.sys_api_restaurant.repository.*;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final MetodoPagoRepository metodoPagoRepository;
    private final ConfiguracionRepository configuracionRepository;

    @Override
    public void run(String... args) {
        Rol rolAdmin = getOrCreateAdminRole();
        createOrUpdateAdminUser(rolAdmin);
        seedMetodosPago();
        seedConfiguracion();
    }

    // ---------------- ADMIN ROLE ----------------
    private Rol getOrCreateAdminRole() {
        return rolRepository.findByNombreRol("ADMIN")
                .orElseGet(() -> {
                    Rol admin = Rol.builder()
                            .nombreRol("ADMIN")
                            .descripcionRol("Administrador del sistema con acceso total")
                            .estadoRol(true)
                            .build();
                    rolRepository.save(admin);
                    System.out.println("Rol ADMIN creado correctamente");
                    return admin;
                });
    }

    // ---------------- ADMIN USER ----------------
    private void createOrUpdateAdminUser(Rol rolAdmin) {
        usuarioRepository.findByNombreUsuario(ADMIN_USERNAME)
                .ifPresentOrElse(
                        this::resetAdminUser,
                        () -> createAdminUser(rolAdmin)
                );
    }

    private void createAdminUser(Rol rolAdmin) {
        Usuario nuevo = Usuario.builder()
                .nombreUsuario(ADMIN_USERNAME)
                .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                .nombres("Administrador")
                .apellidos("del Sistema")
                .email("admin@sistema.com")
                .telefono("999999999")
                .estadoUsuario(true)
                .rol(rolAdmin)
                .build();

        usuarioRepository.save(nuevo);
        System.out.println("Usuario admin creado correctamente (admin / admin123)");
    }

    private void resetAdminUser(Usuario existente) {
        existente.setPasswordHash(passwordEncoder.encode(ADMIN_PASSWORD));
        existente.setEstadoUsuario(true);
        usuarioRepository.save(existente);
        System.out.println("Contraseña de admin restablecida a admin123");
    }

    // ---------------- METODOS DE PAGO ----------------
    private void seedMetodosPago() {
        List<String> metodos = List.of(
                "EFECTIVO", "TARJETA_CREDITO", "TARJETA_DEBITO",
                "YAPE", "PLIN", "TRANSFERENCIA"
        );

        metodos.forEach(this::createMetodoPagoIfNotExists);
    }

    private void createMetodoPagoIfNotExists(String nombre) {
        if (metodoPagoRepository.findByNombreMetodo(nombre).isEmpty()) {
            metodoPagoRepository.save(MetodoPago.builder()
                    .nombreMetodo(nombre)
                    .descripcionMetodo("Método de pago " + nombre.toLowerCase())
                    .estadoMetodo(true)
                    .build());

            System.out.println("MetodoPago " + nombre + " creado correctamente");
        }
    }

    // ---------------- CONFIGURACION ----------------
    private void seedConfiguracion() {
        String[][] configs = {
                {"MONEDA", "PEN", "Moneda oficial del sistema"},
                {"IGV", "18", "Porcentaje de IGV"},
                {"HORA_APERTURA", "08:00", "Hora de apertura del restaurante"},
                {"HORA_CIERRE", "23:00", "Hora de cierre del restaurante"},
                {"NOMBRE_RESTAURANTE", "Mi Restaurante", "Nombre del restaurante"}
        };

        for (String[] c : configs) {
            createConfigIfNotExists(c[0], c[1], c[2]);
        }
    }

    private void createConfigIfNotExists(String clave, String valor, String descripcion) {
        if (configuracionRepository.findByClave(clave).isEmpty()) {
            configuracionRepository.save(Configuracion.builder()
                    .clave(clave)
                    .valor(valor)
                    .descripcion(descripcion)
                    .estadoConfig(true)
                    .build());

            System.out.println("Configuracion " + clave + " creada correctamente");
        }
    }
}
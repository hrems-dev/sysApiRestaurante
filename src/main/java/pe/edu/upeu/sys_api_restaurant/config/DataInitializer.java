package pe.edu.upeu.sys_api_restaurant.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.entity.Rol;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.entity.Configuracion;
import pe.edu.upeu.sys_api_restaurant.entity.MetodoPago;
import pe.edu.upeu.sys_api_restaurant.repository.ConfiguracionRepository;
import pe.edu.upeu.sys_api_restaurant.repository.MetodoPagoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.RolRepository;
import pe.edu.upeu.sys_api_restaurant.repository.UsuarioRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final MetodoPagoRepository metodoPagoRepository;
    private final ConfiguracionRepository configuracionRepository;

    @Override
    public void run(String... args) {
        if (rolRepository.findByNombreRol("ADMIN").isEmpty()) {
            Rol admin = Rol.builder()
                    .nombreRol("ADMIN")
                    .descripcionRol("Administrador del sistema con acceso total")
                    .estadoRol(true)
                    .build();
            rolRepository.save(admin);
            System.out.println("Rol ADMIN creado correctamente");
        }

        Rol rolAdmin = rolRepository.findByNombreRol("ADMIN")
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

        var adminUser = usuarioRepository.findByNombreUsuario("admin");
        if (adminUser.isEmpty()) {
            Usuario nuevoAdmin = Usuario.builder()
                    .nombreUsuario("admin")
                    .passwordHash(passwordEncoder.encode("admin123"))
                    .nombres("Administrador")
                    .apellidos("del Sistema")
                    .email("admin@sistema.com")
                    .telefono("999999999")
                    .estadoUsuario(true)
                    .rol(rolAdmin)
                    .build();
            usuarioRepository.save(nuevoAdmin);
            System.out.println("Usuario admin creado correctamente (admin / admin123)");
        } else {
            Usuario existente = adminUser.get();
            existente.setPasswordHash(passwordEncoder.encode("admin123"));
            existente.setEstadoUsuario(true);
            existente.setRol(rolAdmin);
            usuarioRepository.save(existente);
            System.out.println("Contraseña de admin restablecida a admin123");
        }

        seedMetodosPago();
        seedConfiguracion();
    }

    private void seedMetodosPago() {
        String[] nombres = {"EFECTIVO", "TARJETA_CREDITO", "TARJETA_DEBITO", "YAPE", "PLIN", "TRANSFERENCIA"};
        for (String nombre : nombres) {
            if (metodoPagoRepository.findByNombreMetodo(nombre).isEmpty()) {
                metodoPagoRepository.save(MetodoPago.builder()
                        .nombreMetodo(nombre)
                        .descripcionMetodo("Método de pago " + nombre.toLowerCase())
                        .estadoMetodo(true)
                        .build());
                System.out.println("MetodoPago " + nombre + " creado correctamente");
            }
        }
    }

    private void seedConfiguracion() {
        String[][] configs = {
                {"MONEDA", "PEN", "Moneda oficial del sistema"},
                {"IGV", "18", "Porcentaje de IGV"},
                {"HORA_APERTURA", "08:00", "Hora de apertura del restaurante"},
                {"HORA_CIERRE", "23:00", "Hora de cierre del restaurante"},
                {"NOMBRE_RESTAURANTE", "Mi Restaurante", "Nombre del restaurante"}
        };
        for (String[] c : configs) {
            if (configuracionRepository.findByClave(c[0]).isEmpty()) {
                configuracionRepository.save(Configuracion.builder()
                        .clave(c[0])
                        .valor(c[1])
                        .descripcion(c[2])
                        .estadoConfig(true)
                        .build());
                System.out.println("Configuracion " + c[0] + " creada correctamente");
            }
        }
    }
}

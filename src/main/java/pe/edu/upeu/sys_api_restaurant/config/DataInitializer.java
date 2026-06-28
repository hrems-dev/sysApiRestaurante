package pe.edu.upeu.sys_api_restaurant.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.entity.Rol;
import pe.edu.upeu.sys_api_restaurant.repository.RolRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

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
    }
}

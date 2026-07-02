package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    Optional<Usuario> findByEmail(String email);

    @org.springframework.data.jpa.repository.Query("SELECT MAX(u.codigoEmpleado) FROM Usuario u WHERE u.codigoEmpleado LIKE 'MES-%'")
    String findMaxCodigoEmpleado();
}

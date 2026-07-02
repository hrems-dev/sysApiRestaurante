package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.Notificacion;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByIdUsuarioOrderByFechaNotificacionDesc(Integer idUsuario);
    List<Notificacion> findByLeidoFalseAndIdUsuario(Integer idUsuario);
    List<Notificacion> findByTipoNotificacion(String tipoNotificacion);
}

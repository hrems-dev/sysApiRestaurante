package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.Reserva;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuario_IdUsuario(Integer idUsuario);
    List<Reserva> findByLugarAtencion_IdLugar(Integer idLugar);
    List<Reserva> findByEstadoReserva(String estadoReserva);
    List<Reserva> findByFechaHoraReservaBetween(LocalDateTime inicio, LocalDateTime fin);
}

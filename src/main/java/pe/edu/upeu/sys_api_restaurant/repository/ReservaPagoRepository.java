package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.ReservaPago;

import java.util.List;

@Repository
public interface ReservaPagoRepository extends JpaRepository<ReservaPago, Integer> {
    List<ReservaPago> findByReserva_IdReserva(Integer idReserva);
    List<ReservaPago> findByPago_IdPago(Integer idPago);
}

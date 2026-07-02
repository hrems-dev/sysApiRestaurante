package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.Venta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Integer> {
    Optional<Venta> findByIdPedido(Integer idPedido);
    List<Venta> findByIdPago(Integer idPago);
    List<Venta> findByEstadoVenta(String estadoVenta);
    List<Venta> findByFechaVentaBetween(LocalDateTime inicio, LocalDateTime fin);
}

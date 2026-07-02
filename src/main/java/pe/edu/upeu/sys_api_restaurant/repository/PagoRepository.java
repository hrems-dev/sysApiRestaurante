package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.Pago;

import java.util.List;
import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    Optional<Pago> findByCodigoPago(String codigoPago);
    List<Pago> findByEstadoPago(String estadoPago);
    List<Pago> findByMetodoPago_IdMetodoPago(Integer idMetodoPago);
}

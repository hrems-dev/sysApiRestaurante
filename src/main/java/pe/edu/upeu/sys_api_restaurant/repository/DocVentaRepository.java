package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.DocVenta;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocVentaRepository extends JpaRepository<DocVenta, Integer> {
    Optional<DocVenta> findBySerieAndNumero(String serie, String numero);
    List<DocVenta> findByEstadoDocumentoTrue();
}

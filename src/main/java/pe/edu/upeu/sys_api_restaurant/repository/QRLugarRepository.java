package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.QRLugar;

import java.util.List;
import java.util.Optional;

@Repository
public interface QRLugarRepository extends JpaRepository<QRLugar, Integer> {

    Optional<QRLugar> findByCodigoQR(String codigoQR);

    Optional<QRLugar> findByLugarAtencion_IdLugar(Integer idLugar);

    List<QRLugar> findByEstadoQRTrue();
}

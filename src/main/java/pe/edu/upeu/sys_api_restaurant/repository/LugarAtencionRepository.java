package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;

import java.util.List;
import java.util.Optional;

@Repository
public interface LugarAtencionRepository extends JpaRepository<LugarAtencion, Integer> {

    List<LugarAtencion> findByEstadoLugarTrue();

    List<LugarAtencion> findByTipoLugar(String tipoLugar);

    Optional<LugarAtencion> findByNombreLugar(String nombreLugar);
}

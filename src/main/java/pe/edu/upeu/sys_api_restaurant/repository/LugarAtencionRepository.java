package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;

@Repository
public interface LugarAtencionRepository extends JpaRepository<LugarAtencion, Integer> {
}

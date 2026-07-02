package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.Mesa;

import java.util.List;
import java.util.Optional;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    List<Mesa> findByEstadoMesaTrue();

    List<Mesa> findByLugar_IdLugar(Integer idLugar);

    Optional<Mesa> findByNombreMesaAndLugar_IdLugar(String nombreMesa, Integer idLugar);
}

package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.Producto;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {

    List<Producto> findByCategoria_IdCategoria(Integer idCategoria);

    List<Producto> findByEstadoProductoTrue();

    List<Producto> findByNombreProductoContainingIgnoreCase(String nombreProducto);

    Optional<Producto> findByNombreProducto(String nombreProducto);
}

package pe.edu.upeu.sys_api_restaurant.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByUsuario_IdUsuario(Integer idUsuario);

    List<Pedido> findByEstadoPedido(String estadoPedido);

    List<Pedido> findByLugarAtencion_IdLugar(Integer idLugar);

    List<Pedido> findByPagadoTrue();

    List<Pedido> findByPagadoFalse();

    List<Pedido> findByEstadoPedidoIn(List<String> estados);
}

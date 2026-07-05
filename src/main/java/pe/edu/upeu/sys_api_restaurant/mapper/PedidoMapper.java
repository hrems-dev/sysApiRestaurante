package pe.edu.upeu.sys_api_restaurant.mapper;

import pe.edu.upeu.sys_api_restaurant.dto.DetallePedidoResponse;
import pe.edu.upeu.sys_api_restaurant.dto.PedidoResponse;
import pe.edu.upeu.sys_api_restaurant.entity.DetallePedido;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;
import pe.edu.upeu.sys_api_restaurant.entity.Producto;

public class PedidoMapper {

    public static PedidoResponse toPedidoResponse(Pedido entity) {
        return new PedidoResponse(
                entity.getIdPedido(),
                entity.getUsuario().getNombres() + " " + entity.getUsuario().getApellidos(),
                entity.getLugarAtencion() != null ? entity.getLugarAtencion().getNombreLugar() : null,
                entity.getTipoPedido(),
                entity.getEstadoPedido(),
                entity.getDireccionEntrega(),
                entity.getObservacionPedido(),
                entity.getFechaHoraPedido(),
                entity.getTotalPedido(),
                entity.getPagado(),
                entity.getDetalles().stream()
                        .map(PedidoMapper::toDetallePedidoResponse)
                        .toList()
        );
    }

    public static DetallePedidoResponse toDetallePedidoResponse(DetallePedido entity) {
        return new DetallePedidoResponse(
                entity.getIdDetalle(),
                entity.getIdProducto(),
                null,
                entity.getCantidad(),
                entity.getPrecioUnitario(),
                entity.getSubtotal(),
                entity.getObservacionDetalle(),
                entity.getEstadoDetalle()
        );
    }

    public static DetallePedidoResponse toDetallePedidoResponse(DetallePedido entity, Producto producto) {
        return new DetallePedidoResponse(
                entity.getIdDetalle(),
                entity.getIdProducto(),
                producto != null ? producto.getNombreProducto() : null,
                entity.getCantidad(),
                entity.getPrecioUnitario(),
                entity.getSubtotal(),
                entity.getObservacionDetalle(),
                entity.getEstadoDetalle()
        );
    }
}
package pe.edu.upeu.sys_api_restaurant.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.dto.CocinaDetalleDTO;
import pe.edu.upeu.sys_api_restaurant.dto.CocinaPedidoResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;

import java.util.List;
import java.util.Optional;

@Component
public class CocinaMapper {

    public static CocinaPedidoResponse toCocinaPedidoResponse(Pedido pedido) {

        List<CocinaDetalleDTO> detalles = Optional.ofNullable(pedido.getDetalles())
                .orElse(List.of())
                .stream()
                .map(d -> new CocinaDetalleDTO(
                        d.getIdDetalle(),
                        d.getIdProducto(),
                        d.getCantidad(),
                        d.getPrecioUnitario(),
                        d.getSubtotal(),
                        d.getObservacionDetalle()
                ))
                .toList();

        return new CocinaPedidoResponse(
                pedido.getIdPedido(),
                pedido.getLugarAtencion() != null ? pedido.getLugarAtencion().getNombreLugar() : null,
                detalles,
                pedido.getEstadoPedido(),
                pedido.getFechaHoraPedido(),
                pedido.getObservacionPedido()
        );
    }
}
package pe.edu.upeu.sys_api_restaurant.dto;

import java.time.LocalDateTime;
import java.util.List;


public record CocinaPedidoResponse(
        Integer idPedido,
        String mesa,
        List<CocinaDetalleDTO> productos,
        String estadoPedido,
        LocalDateTime fechaHoraPedido,
        String observacion
) {
}

package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponse(
        Integer idPedido,
        String nombreUsuario,
        String nombreLugar,
        String tipoPedido,
        String estadoPedido,
        String direccionEntrega,
        String observacionPedido,
        LocalDateTime fechaHoraPedido,
        BigDecimal totalPedido,
        Boolean pagado,
        List<DetallePedidoResponse> detalles
) {}

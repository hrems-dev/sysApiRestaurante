package pe.edu.upeu.sys_api_restaurant.dto;

import java.time.LocalDateTime;

public record DeliveryResponse(
        Integer idPedido,
        String nombreRepartidor,
        String direccionEntrega,
        String estadoPedido,
        LocalDateTime fechaHoraPedido
) {
}

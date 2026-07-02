package pe.edu.upeu.sys_api_restaurant.dto;

import java.time.LocalDateTime;

public record NotificacionResponse(
        Integer idNotificacion,
        Integer idUsuario,
        Integer idPedido,
        Integer idVenta,
        String tipoNotificacion,
        String titulo,
        String descripcion,
        Boolean leido,
        LocalDateTime fechaNotificacion
) {
}

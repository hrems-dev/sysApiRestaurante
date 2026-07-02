package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificacionRequest(
        @NotNull Integer idUsuario,
        Integer idPedido,
        Integer idVenta,
        @NotBlank String tipoNotificacion,
        @NotBlank String titulo,
        @NotBlank String descripcion
) {
}

package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DeliveryRequest(
        @NotNull Integer idPedido,
        @NotNull Integer idRepartidor,
        @NotBlank String direccionEntrega
) {
}

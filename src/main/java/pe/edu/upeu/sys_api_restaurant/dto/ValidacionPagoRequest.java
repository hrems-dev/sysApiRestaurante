package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record ValidacionPagoRequest(
        @NotBlank String codigoPago,
        @NotBlank String estadoPago
) {
}

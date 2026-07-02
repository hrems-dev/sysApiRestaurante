package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PagoRequest(
        @NotNull Integer idMetodoPago,
        @NotBlank String codigoPago,
        @NotNull @DecimalMin("0.01") BigDecimal montoPago,
        String referenciaPago
) {
}

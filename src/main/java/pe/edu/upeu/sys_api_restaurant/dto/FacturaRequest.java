package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FacturaRequest(
        @NotNull Integer idVenta,
        @NotBlank String tipoDocumento
) {
}

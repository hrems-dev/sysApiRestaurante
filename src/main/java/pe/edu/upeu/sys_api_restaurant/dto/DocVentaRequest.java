package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record DocVentaRequest(
        @NotBlank String tipoDocumento,
        @NotBlank String serie,
        @NotBlank String numero
) {
}

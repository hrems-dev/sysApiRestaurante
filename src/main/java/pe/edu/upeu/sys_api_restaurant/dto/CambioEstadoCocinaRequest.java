package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record CambioEstadoCocinaRequest(
        @NotBlank String estado
) {
}

package pe.edu.upeu.sys_api_restaurant.dto;


import jakarta.validation.constraints.NotBlank;

public record ConfiguracionRequest(
        @NotBlank String clave,
        @NotBlank String valor,
        String descripcion
) {
}

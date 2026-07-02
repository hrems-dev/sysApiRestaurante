package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoriaProductoRequest(
        @NotBlank String nombreCategoria,
        String descripcionCategoria
) {}

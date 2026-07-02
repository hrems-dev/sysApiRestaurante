package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductoRequest(
        @NotNull Integer idCategoria,
        @NotBlank String nombreProducto,
        String descripcionProducto,
        @NotNull @DecimalMin("0.01") BigDecimal precioProducto,
        Integer stockProducto,
        String imagenProducto
) {}

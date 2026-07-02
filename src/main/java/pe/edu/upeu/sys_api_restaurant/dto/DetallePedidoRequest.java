package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record DetallePedidoRequest(
        @NotNull Integer idProducto,
        @NotNull @Min(1) Integer cantidad,
        @NotNull BigDecimal precioUnitario,
        String observacionDetalle
) {}

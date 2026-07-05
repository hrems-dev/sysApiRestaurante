package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;


public record CocinaDetalleDTO(
        Integer idDetalle,
        Integer idProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal,
        String observacionDetalle
) {
}

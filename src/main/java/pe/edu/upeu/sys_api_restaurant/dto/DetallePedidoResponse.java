package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;

public record DetallePedidoResponse(
        Integer idDetalle,
        Integer idProducto,
        String nombreProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal,
        String observacionDetalle,
        Boolean estadoDetalle
) {}

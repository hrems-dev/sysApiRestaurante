package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;

public record ProductoResponse(
        Integer idProducto,
        Integer idCategoria,
        String nombreCategoria,
        String nombreProducto,
        String descripcionProducto,
        BigDecimal precioProducto,
        Integer stockProducto,
        String imagenProducto,
        Boolean estadoProducto
) {}

package pe.edu.upeu.sys_api_restaurant.dto;


public record CategoriaProductoResponse(
        Integer idCategoria,
        String nombreCategoria,
        String descripcionCategoria,
        Boolean estadoCategoria
) {}

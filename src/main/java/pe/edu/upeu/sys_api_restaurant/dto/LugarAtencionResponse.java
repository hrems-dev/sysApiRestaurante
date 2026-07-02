package pe.edu.upeu.sys_api_restaurant.dto;

public record LugarAtencionResponse(
        Integer idLugar,
        String nombreLugar,
        String tipoLugar,
        String direccion,
        Integer capacidadMaxima,
        Boolean estadoLugar,
        String observacion
) {}

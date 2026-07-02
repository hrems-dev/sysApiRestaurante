package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record LugarAtencionRequest(
        @NotBlank String nombreLugar,
        @NotBlank String tipoLugar,
        String direccion,
        Integer capacidadMaxima,
        String observacion
) {}

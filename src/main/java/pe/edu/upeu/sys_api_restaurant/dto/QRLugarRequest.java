package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QRLugarRequest(
        @NotNull Integer idLugar,
        @NotBlank String codigoQR,
        String urlQR
) {}

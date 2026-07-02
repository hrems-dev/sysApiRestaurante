package pe.edu.upeu.sys_api_restaurant.dto;

import java.time.LocalDateTime;

public record QRLugarResponse(
        Integer idQR,
        Integer idLugar,
        String nombreLugar,
        String codigoQR,
        String urlQR,
        LocalDateTime fechaGeneracion,
        Boolean estadoQR
) {}

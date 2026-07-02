package pe.edu.upeu.sys_api_restaurant.dto;

import java.time.LocalDateTime;

public record DocVentaResponse(
        Integer idDocVenta,
        String tipoDocumento,
        String serie,
        String numero,
        LocalDateTime fechaEmision,
        Boolean estadoDocumento
) {
}

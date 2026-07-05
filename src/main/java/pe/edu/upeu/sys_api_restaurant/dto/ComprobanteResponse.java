package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;


public record ComprobanteResponse(
        Integer idDocVenta,
        String tipoDocumento,
        String serie,
        String numero,
        BigDecimal totalVenta,
        LocalDateTime fechaEmision,
        String estadoDocumento,
        Integer idVenta,
        Integer idPedido
) {
}

package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record VentaResponse(
        Integer idVenta,
        Integer idPedido,
        Integer idPago,
        Integer idDocVenta,
        String tipoDocumento,
        String serie,
        String numero,
        BigDecimal totalVenta,
        LocalDateTime fechaVenta,
        String estadoVenta
) {
}

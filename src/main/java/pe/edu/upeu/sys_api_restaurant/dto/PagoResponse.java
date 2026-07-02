package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagoResponse(
        Integer idPago,
        String nombreMetodo,
        String codigoPago,
        BigDecimal montoPago,
        String estadoPago,
        LocalDateTime fechaPago,
        String referenciaPago
) {
}

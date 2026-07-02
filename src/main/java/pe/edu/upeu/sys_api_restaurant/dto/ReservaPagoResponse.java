package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaPagoResponse(
        Integer idReservaPago,
        Integer idReserva,
        Integer idPago,
        BigDecimal montoAplicado,
        String estadoPagoReserva,
        LocalDateTime fechaAplicacion
) {
}

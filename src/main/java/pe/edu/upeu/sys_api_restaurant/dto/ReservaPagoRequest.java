package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ReservaPagoRequest(
        @NotNull Integer idReserva,
        @NotNull Integer idPago,
        @NotNull BigDecimal montoAplicado
) {
}

package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReservaResponse(
        Integer idReserva,
        String nombreUsuario,
        String nombreLugar,
        LocalDateTime fechaHoraReserva,
        Integer cantidadPersonas,
        String estadoReserva,
        BigDecimal adelantoReserva,
        String observacionReserva,
        LocalDateTime fechaCreacion
) {
}

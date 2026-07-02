package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ReservaRequest(
        @NotNull Integer idUsuario,
        @NotNull Integer idLugar,
        @NotNull LocalDateTime fechaHoraReserva,
        @Min(1) Integer cantidadPersonas,
        String observacionReserva
) {
}

package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CierreCajaRequest(
        @NotNull LocalDate fechaCierre,
        String observacion
) {
}

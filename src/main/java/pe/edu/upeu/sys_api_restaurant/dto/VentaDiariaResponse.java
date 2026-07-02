package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record VentaDiariaResponse(
        LocalDate fecha,
        BigDecimal totalVentas,
        int cantidadVentas,
        BigDecimal totalEfectivo,
        BigDecimal totalTarjeta,
        BigDecimal totalYape
) {
}

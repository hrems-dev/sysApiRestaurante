package pe.edu.upeu.sys_api_restaurant.dto;


import java.math.BigDecimal;
import java.time.LocalDate;

public record CierreCajaResponse(
        Integer idCierre,
        LocalDate fecha,
        BigDecimal totalVentas,
        BigDecimal totalPagos,
        BigDecimal diferencia,
        String observacion,
        String estado
) {
}

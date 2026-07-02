package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ReporteVentasResponse(
        String periodo,
        BigDecimal totalVentas,
        long cantidadVentas,
        Map<String, BigDecimal> ventasPorDia
) {
}

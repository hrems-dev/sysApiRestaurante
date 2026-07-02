package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;

public record ReporteMesasResponse(
        String nombreMesa,
        long cantidadUsos,
        BigDecimal ocupacionPromedio
) {
}

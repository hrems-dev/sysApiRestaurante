package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;

public record ReporteContableResponse(
        String periodo,
        BigDecimal ingresos,
        BigDecimal egresos,
        BigDecimal saldo,
        int cantidadVentas,
        int cantidadPedidos,
        int cantidadReservas
) {
}

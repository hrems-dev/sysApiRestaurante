package pe.edu.upeu.sys_api_restaurant.dto;

import java.math.BigDecimal;

public record ReporteEntregasResponse(
        String nombreRepartidor,
        long cantidadEntregas,
        BigDecimal tiempoPromedioMinutos
) {
}

package pe.edu.upeu.sys_api_restaurant.dto;

import java.util.Map;

public record ReporteReservasResponse(
        String periodo,
        long totalReservas,
        Map<String, Integer> reservasPorEstado
) {
}

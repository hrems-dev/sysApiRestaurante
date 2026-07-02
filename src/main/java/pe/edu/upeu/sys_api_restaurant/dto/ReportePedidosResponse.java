package pe.edu.upeu.sys_api_restaurant.dto;

import java.util.Map;

public record ReportePedidosResponse(
        String periodo,
        long totalPedidos,
        Map<String, Integer> pedidosPorEstado
) {
}

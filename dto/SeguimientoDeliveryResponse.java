package pe.edu.upeu.sys_api_restaurant.dto;

import java.time.LocalDateTime;

public record SeguimientoDeliveryResponse(
        Integer idPedido,
        String estadoActual,
        LocalDateTime fechaEstado,
        String tiempoTranscurrido
) {
}

package pe.edu.upeu.sys_api_restaurant.dto;

public record MetodoPagoResponse(
        Integer idMetodoPago,
        String nombreMetodo,
        String descripcionMetodo,
        Boolean estadoMetodo
) {
}

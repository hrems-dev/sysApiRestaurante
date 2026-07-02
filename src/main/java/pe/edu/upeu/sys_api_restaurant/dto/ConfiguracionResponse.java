package pe.edu.upeu.sys_api_restaurant.dto;

public record ConfiguracionResponse(
        Integer idConfig,
        String clave,
        String valor,
        String descripcion,
        Boolean estadoConfig
) {
}

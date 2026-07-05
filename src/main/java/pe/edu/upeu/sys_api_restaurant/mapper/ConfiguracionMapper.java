package pe.edu.upeu.sys_api_restaurant.mapper;

import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.*;

public class ConfiguracionMapper {

    public static ConfiguracionResponse toConfiguracionResponse(Configuracion entity) {
        return new ConfiguracionResponse(
                entity.getIdConfig(),
                entity.getClave(),
                entity.getValor(),
                entity.getDescripcion(),
                entity.getEstadoConfig()
        );
    }

    public static Configuracion toConfiguracion(ConfiguracionRequest request) {
        return Configuracion.builder()
                .clave(request.clave())
                .valor(request.valor())
                .descripcion(request.descripcion())
                .estadoConfig(true)
                .build();
    }

    public static MetodoPagoResponse toMetodoPagoResponse(MetodoPago entity) {
        return new MetodoPagoResponse(
                entity.getIdMetodoPago(),
                entity.getNombreMetodo(),
                entity.getDescripcionMetodo(),
                entity.getEstadoMetodo()
        );
    }

    public static MetodoPago toMetodoPago(MetodoPagoRequest request) {
        return MetodoPago.builder()
                .nombreMetodo(request.nombreMetodo())
                .descripcionMetodo(request.descripcionMetodo())
                .estadoMetodo(true)
                .build();
    }
}
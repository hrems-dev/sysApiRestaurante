package pe.edu.upeu.sys_api_restaurant.mapper;

import pe.edu.upeu.sys_api_restaurant.dto.NotificacionRequest;
import pe.edu.upeu.sys_api_restaurant.dto.NotificacionResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Notificacion;

public class NotificacionMapper {

    public static NotificacionResponse toNotificacionResponse(Notificacion entity) {
        return new NotificacionResponse(
                entity.getIdNotificacion(),
                entity.getIdUsuario(),
                entity.getIdPedido(),
                entity.getIdVenta(),
                entity.getTipoNotificacion(),
                entity.getTitulo(),
                entity.getDescripcion(),
                entity.getLeido(),
                entity.getFechaNotificacion()
        );
    }

    public static Notificacion toNotificacion(NotificacionRequest request) {
        return Notificacion.builder()
                .idUsuario(request.idUsuario())
                .idPedido(request.idPedido())
                .idVenta(request.idVenta())
                .tipoNotificacion(request.tipoNotificacion())
                .titulo(request.titulo())
                .descripcion(request.descripcion())
                .leido(false)
                .build();
    }
}
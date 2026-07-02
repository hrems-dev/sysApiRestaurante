package pe.edu.upeu.sys_api_restaurant.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.Notificacion;

@Component
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

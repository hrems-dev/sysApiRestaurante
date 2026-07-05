package pe.edu.upeu.sys_api_restaurant.mapper;

import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.*;

public class ReservaMapper {

    public static ReservaResponse toReservaResponse(Reserva entity) {
        return new ReservaResponse(
                entity.getIdReserva(),
                entity.getUsuario().getNombres() + " " + entity.getUsuario().getApellidos(),
                entity.getLugarAtencion().getNombreLugar(),
                entity.getFechaHoraReserva(),
                entity.getCantidadPersonas(),
                entity.getEstadoReserva(),
                entity.getAdelantoReserva(),
                entity.getObservacionReserva(),
                entity.getFechaCreacion()
        );
    }

    public static Reserva toReserva(ReservaRequest request) {
        return Reserva.builder()
                .fechaHoraReserva(request.fechaHoraReserva())
                .cantidadPersonas(request.cantidadPersonas())
                .observacionReserva(request.observacionReserva())
                .build();
    }

    public static ReservaPagoResponse toReservaPagoResponse(ReservaPago entity) {
        return new ReservaPagoResponse(
                entity.getIdReservaPago(),
                entity.getReserva().getIdReserva(),
                entity.getPago().getIdPago(),
                entity.getMontoAplicado(),
                entity.getEstadoPagoReserva(),
                entity.getFechaAplicacion()
        );
    }

    public static ReservaPago toReservaPago(ReservaPagoRequest request) {
        return ReservaPago.builder()
                .montoAplicado(request.montoAplicado())
                .estadoPagoReserva("pendiente")
                .build();
    }
}
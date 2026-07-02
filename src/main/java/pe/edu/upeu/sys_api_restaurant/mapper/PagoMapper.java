package pe.edu.upeu.sys_api_restaurant.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.dto.PagoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.PagoResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Pago;

@Component
public class PagoMapper {

    public static PagoResponse toPagoResponse(Pago entity) {
        return new PagoResponse(
                entity.getIdPago(),
                entity.getMetodoPago().getNombreMetodo(),
                entity.getCodigoPago(),
                entity.getMontoPago(),
                entity.getEstadoPago(),
                entity.getFechaPago(),
                entity.getReferenciaPago()
        );
    }

    public static Pago toPago(PagoRequest request) {
        return Pago.builder()
                .codigoPago(request.codigoPago())
                .montoPago(request.montoPago())
                .estadoPago("pendiente")
                .referenciaPago(request.referenciaPago())
                .build();
    }
}

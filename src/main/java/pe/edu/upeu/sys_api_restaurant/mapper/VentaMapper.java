package pe.edu.upeu.sys_api_restaurant.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.DocVenta;
import pe.edu.upeu.sys_api_restaurant.entity.Venta;

@Component
public class VentaMapper {

    public static VentaResponse toVentaResponse(Venta entity) {
        return new VentaResponse(
                entity.getIdVenta(),
                entity.getIdPedido(),
                entity.getIdPago(),
                entity.getDocVenta().getIdDocVenta(),
                entity.getDocVenta().getTipoDocumento(),
                entity.getDocVenta().getSerie(),
                entity.getDocVenta().getNumero(),
                entity.getTotalVenta(),
                entity.getFechaVenta(),
                entity.getEstadoVenta()
        );
    }

    public static DocVentaResponse toDocVentaResponse(DocVenta entity) {
        return new DocVentaResponse(
                entity.getIdDocVenta(),
                entity.getTipoDocumento(),
                entity.getSerie(),
                entity.getNumero(),
                entity.getFechaEmision(),
                entity.getEstadoDocumento()
        );
    }

    public static DocVenta toDocVenta(DocVentaRequest request) {
        return DocVenta.builder()
                .tipoDocumento(request.tipoDocumento())
                .serie(request.serie())
                .numero(request.numero())
                .estadoDocumento(true)
                .build();
    }
}

package pe.edu.upeu.sys_api_restaurant.service;

import org.springframework.stereotype.Service;
import pe.edu.upeu.sys_api_restaurant.dto.ComprobanteResponse;
import pe.edu.upeu.sys_api_restaurant.entity.DocVenta;
import pe.edu.upeu.sys_api_restaurant.entity.Venta;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class EmisionDocService {

    private static final AtomicInteger contadorFactura = new AtomicInteger(1);
    private static final AtomicInteger contadorBoleta = new AtomicInteger(1);

    public String generarSerie(String tipoDocumento) {
        String anio = String.valueOf(LocalDateTime.now().getYear());
        return switch (tipoDocumento.toUpperCase()) {
            case "FACTURA" -> "F" + anio;
            case "BOLETA" -> "B" + anio;
            default -> "T" + anio;
        };
    }

    public String generarNumero(String serie) {
        int numero = "FACTURA".equalsIgnoreCase(
                serie.startsWith("F") ? "FACTURA" : "BOLETA"
        ) ? contadorFactura.getAndIncrement() : contadorBoleta.getAndIncrement();
        return String.format("%08d", numero);
    }

    public ComprobanteResponse formatearComprobante(Venta venta, DocVenta doc) {
        return new ComprobanteResponse(
                doc.getIdDocVenta(),
                doc.getTipoDocumento(),
                doc.getSerie(),
                doc.getNumero(),
                venta.getTotalVenta(),
                doc.getFechaEmision(),
                doc.getEstadoDocumento() ? "ACTIVO" : "ANULADO",
                venta.getIdVenta(),
                venta.getIdPedido()
        );
    }
}

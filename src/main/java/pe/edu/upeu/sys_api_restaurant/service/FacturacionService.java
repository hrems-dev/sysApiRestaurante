package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.ComprobanteResponse;
import pe.edu.upeu.sys_api_restaurant.dto.FacturaRequest;
import pe.edu.upeu.sys_api_restaurant.dto.FacturaResponse;
import pe.edu.upeu.sys_api_restaurant.entity.DocVenta;
import pe.edu.upeu.sys_api_restaurant.entity.Venta;
import pe.edu.upeu.sys_api_restaurant.repository.DocVentaRepository;
import pe.edu.upeu.sys_api_restaurant.repository.VentaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacturacionService {

    private final VentaRepository ventaRepository;
    private final DocVentaRepository docVentaRepository;
    private final EmisionDocService emisionDocService;

    @Transactional(readOnly = true)
    public List<FacturaResponse> listarComprobantes() {
        return docVentaRepository.findByEstadoDocumentoTrue()
                .stream()
                .map(doc -> {
                    Venta venta = ventaRepository.findAll().stream()
                            .filter(v -> doc.equals(v.getDocVenta()))
                            .findFirst()
                            .orElse(null);
                    return new FacturaResponse(
                            doc.getIdDocVenta(),
                            doc.getTipoDocumento(),
                            doc.getSerie(),
                            doc.getNumero(),
                            venta != null ? venta.getTotalVenta() : null,
                            doc.getFechaEmision()
                    );
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public ComprobanteResponse obtenerComprobante(Integer idDocVenta) {
        DocVenta doc = docVentaRepository.findById(idDocVenta)
                .orElseThrow(() -> new ResourceNotFoundException("DocVenta no encontrado con id: " + idDocVenta));
        Venta venta = ventaRepository.findAll().stream()
                .filter(v -> doc.equals(v.getDocVenta()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada para doc: " + idDocVenta));
        return emisionDocService.formatearComprobante(venta, doc);
    }

    @Transactional
    public FacturaResponse emitirComprobante(FacturaRequest request) {
        Venta venta = ventaRepository.findById(request.idVenta())
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + request.idVenta()));

        String serie = emisionDocService.generarSerie(request.tipoDocumento());
        String numero = emisionDocService.generarNumero(serie);

        DocVenta doc = DocVenta.builder()
                .tipoDocumento(request.tipoDocumento())
                .serie(serie)
                .numero(numero)
                .build();

        doc = docVentaRepository.save(doc);

        venta.setDocVenta(doc);
        ventaRepository.save(venta);

        return new FacturaResponse(
                doc.getIdDocVenta(),
                doc.getTipoDocumento(),
                doc.getSerie(),
                doc.getNumero(),
                venta.getTotalVenta(),
                doc.getFechaEmision()
        );
    }
}

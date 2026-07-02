package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.DocVenta;
import pe.edu.upeu.sys_api_restaurant.mapper.VentaMapper;
import pe.edu.upeu.sys_api_restaurant.repository.DocVentaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DocVentaService {

    private final DocVentaRepository repository;

    @Transactional(readOnly = true)
    public List<DocVentaResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(VentaMapper::toDocVentaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public DocVentaResponse findById(Integer id) {
        return repository.findById(id)
                .map(VentaMapper::toDocVentaResponse)
                .orElseThrow(() -> new ResourceNotFoundException("DocVenta no encontrada con id: " + id));
    }

    @Transactional
    public DocVentaResponse create(DocVentaRequest request) {
        DocVenta entity = VentaMapper.toDocVenta(request);
        return VentaMapper.toDocVentaResponse(repository.save(entity));
    }

    @Transactional
    public String generarCorrelativo(String tipoDocumento) {
        String serie = switch (tipoDocumento.toLowerCase()) {
            case "boleta" -> "B001";
            case "factura" -> "F001";
            case "ticket" -> "T001";
            default -> "G001";
        };

        List<DocVenta> existing = repository.findAll();
        int maxNumero = existing.stream()
                .filter(d -> d.getTipoDocumento().equalsIgnoreCase(tipoDocumento))
                .mapToInt(d -> {
                    try {
                        return Integer.parseInt(d.getNumero());
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);

        String numero = String.format("%08d", maxNumero + 1);
        return serie + "-" + numero;
    }
}

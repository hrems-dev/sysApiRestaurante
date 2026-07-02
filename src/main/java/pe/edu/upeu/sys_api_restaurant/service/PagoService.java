package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.entity.MetodoPago;
import pe.edu.upeu.sys_api_restaurant.repository.MetodoPagoRepository;
import pe.edu.upeu.sys_api_restaurant.dto.PagoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.PagoResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Pago;
import pe.edu.upeu.sys_api_restaurant.mapper.PagoMapper;
import pe.edu.upeu.sys_api_restaurant.repository.PagoRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository repository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final ValidacionPagoService validacionPagoService;

    @Transactional(readOnly = true)
    public List<PagoResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(PagoMapper::toPagoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PagoResponse findById(Integer id) {
        return repository.findById(id)
                .map(PagoMapper::toPagoResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public PagoResponse findByCodigo(String codigoPago) {
        return repository.findByCodigoPago(codigoPago)
                .map(PagoMapper::toPagoResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con codigo: " + codigoPago));
    }

    @Transactional(readOnly = true)
    public List<PagoResponse> findByEstado(String estado) {
        return repository.findByEstadoPago(estado)
                .stream()
                .map(PagoMapper::toPagoResponse)
                .toList();
    }

    @Transactional
    public PagoResponse registrarPago(PagoRequest request) {
        MetodoPago metodoPago = metodoPagoRepository.findById(request.idMetodoPago())
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago no encontrado con id: " + request.idMetodoPago()));

        Pago entity = PagoMapper.toPago(request);
        entity.setMetodoPago(metodoPago);
        return PagoMapper.toPagoResponse(repository.save(entity));
    }

    @Transactional
    public PagoResponse validarPago(String codigoPago, String estado) {
        Pago pago = repository.findByCodigoPago(codigoPago)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con codigo: " + codigoPago));
        pago.setEstadoPago(estado);
        return PagoMapper.toPagoResponse(repository.save(pago));
    }

    @Transactional
    public void delete(Integer id) {
        Pago pago = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + id));
        repository.delete(pago);
    }

    public boolean simularPasarela(BigDecimal monto) {
        return validacionPagoService.validar(Pago.builder().montoPago(monto).build());
    }
}

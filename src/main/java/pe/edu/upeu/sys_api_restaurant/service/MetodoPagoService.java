package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.MetodoPago;
import pe.edu.upeu.sys_api_restaurant.mapper.ConfiguracionMapper;
import pe.edu.upeu.sys_api_restaurant.repository.MetodoPagoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetodoPagoService {

    private final MetodoPagoRepository repository;

    @Transactional(readOnly = true)
    public List<MetodoPagoResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(ConfiguracionMapper::toMetodoPagoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MetodoPagoResponse> findAllActive() {
        return repository.findByEstadoMetodoTrue()
                .stream()
                .map(ConfiguracionMapper::toMetodoPagoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public MetodoPagoResponse findById(Integer id) {
        return repository.findById(id)
                .map(ConfiguracionMapper::toMetodoPagoResponse)
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago no encontrado con id: " + id));
    }

    @Transactional
    public MetodoPagoResponse create(MetodoPagoRequest request) {
        MetodoPago entity = ConfiguracionMapper.toMetodoPago(request);
        return ConfiguracionMapper.toMetodoPagoResponse(repository.save(entity));
    }

    @Transactional
    public MetodoPagoResponse update(Integer id, MetodoPagoRequest request) {
        MetodoPago existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago no encontrado con id: " + id));
        existing.setNombreMetodo(request.nombreMetodo());
        existing.setDescripcionMetodo(request.descripcionMetodo());
        return ConfiguracionMapper.toMetodoPagoResponse(repository.save(existing));
    }

    @Transactional
    public void delete(Integer id) {
        MetodoPago existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("MetodoPago no encontrado con id: " + id));
        repository.delete(existing);
    }
}

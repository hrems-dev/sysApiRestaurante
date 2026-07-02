package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.LugarAtencionRequest;
import pe.edu.upeu.sys_api_restaurant.dto.LugarAtencionResponse;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.mapper.LugarAtencionMapper;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LugarAtencionService {

    private final LugarAtencionRepository repository;

    @Transactional(readOnly = true)
    public List<LugarAtencionResponse> findAll() {
        return repository.findAll().stream()
                .map(LugarAtencionMapper::toLugarAtencionResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<LugarAtencionResponse> findAllActive() {
        return repository.findByEstadoLugarTrue().stream()
                .map(LugarAtencionMapper::toLugarAtencionResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LugarAtencionResponse findById(Integer id) {
        LugarAtencion entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LugarAtencion no encontrado con id: " + id));
        return LugarAtencionMapper.toLugarAtencionResponse(entity);
    }

    @Transactional
    public LugarAtencionResponse create(LugarAtencionRequest request) {
        LugarAtencion entity = LugarAtencion.builder()
                .nombreLugar(request.nombreLugar())
                .tipoLugar(request.tipoLugar())
                .direccion(request.direccion())
                .capacidadMaxima(request.capacidadMaxima())
                .estadoLugar(true)
                .observacion(request.observacion())
                .build();
        return LugarAtencionMapper.toLugarAtencionResponse(repository.save(entity));
    }

    @Transactional
    public LugarAtencionResponse update(Integer id, LugarAtencionRequest request) {
        LugarAtencion existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LugarAtencion no encontrado con id: " + id));
        existing.setNombreLugar(request.nombreLugar());
        existing.setTipoLugar(request.tipoLugar());
        existing.setDireccion(request.direccion());
        existing.setCapacidadMaxima(request.capacidadMaxima());
        existing.setObservacion(request.observacion());
        return LugarAtencionMapper.toLugarAtencionResponse(repository.save(existing));
    }

    @Transactional
    public void delete(Integer id) {
        LugarAtencion existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("LugarAtencion no encontrado con id: " + id));
        existing.setEstadoLugar(false);
        repository.save(existing);
    }

    @Transactional(readOnly = true)
    public List<LugarAtencionResponse> findByTipo(String tipo) {
        return repository.findByTipoLugar(tipo).stream()
                .map(LugarAtencionMapper::toLugarAtencionResponse)
                .toList();
    }
}

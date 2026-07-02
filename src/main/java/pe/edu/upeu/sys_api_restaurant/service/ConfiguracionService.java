package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.Configuracion;
import pe.edu.upeu.sys_api_restaurant.mapper.ConfiguracionMapper;
import pe.edu.upeu.sys_api_restaurant.repository.ConfiguracionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConfiguracionService {

    private final ConfiguracionRepository repository;

    @Transactional(readOnly = true)
    public List<ConfiguracionResponse> obtenerTodas() {
        return repository.findAll()
                .stream()
                .map(ConfiguracionMapper::toConfiguracionResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ConfiguracionResponse obtenerPorClave(String clave) {
        return repository.findByClave(clave)
                .map(ConfiguracionMapper::toConfiguracionResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Configuracion no encontrada con clave: " + clave));
    }

    @Transactional
    public ConfiguracionResponse crear(ConfiguracionRequest request) {
        Configuracion entity = ConfiguracionMapper.toConfiguracion(request);
        return ConfiguracionMapper.toConfiguracionResponse(repository.save(entity));
    }

    @Transactional
    public ConfiguracionResponse actualizar(Integer id, ConfiguracionRequest request) {
        Configuracion existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuracion no encontrada con id: " + id));
        existing.setClave(request.clave());
        existing.setValor(request.valor());
        existing.setDescripcion(request.descripcion());
        return ConfiguracionMapper.toConfiguracionResponse(repository.save(existing));
    }

    @Transactional
    public void eliminar(Integer id) {
        Configuracion existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Configuracion no encontrada con id: " + id));
        repository.delete(existing);
    }

    @Transactional(readOnly = true)
    public String obtenerValor(String clave) {
        return repository.findByClave(clave)
                .map(Configuracion::getValor)
                .orElseThrow(() -> new ResourceNotFoundException("Configuracion no encontrada con clave: " + clave));
    }
}

package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LugarAtencionService {

    private final LugarAtencionRepository repository;

    @Transactional(readOnly = true)
    public List<LugarAtencion> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public LugarAtencion findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar de atención no encontrado con id: " + id));
    }

    @Transactional
    public LugarAtencion save(LugarAtencion lugar) {
        return repository.save(lugar);
    }

    @Transactional
    public LugarAtencion update(Integer id, LugarAtencion lugar) {
        LugarAtencion existing = findById(id);
        existing.setNombreLugar(lugar.getNombreLugar());
        existing.setTipoLugar(lugar.getTipoLugar());
        existing.setDireccion(lugar.getDireccion());
        existing.setCapacidadMaxima(lugar.getCapacidadMaxima());
        existing.setEstadoLugar(lugar.getEstadoLugar());
        existing.setObservacion(lugar.getObservacion());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        LugarAtencion existing = findById(id);
        repository.delete(existing);
    }
}

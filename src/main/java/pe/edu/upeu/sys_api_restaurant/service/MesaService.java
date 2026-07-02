package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.entity.Mesa;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;
import pe.edu.upeu.sys_api_restaurant.repository.MesaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MesaService {

    private final MesaRepository repository;
    private final LugarAtencionRepository lugarRepo;

    @Transactional(readOnly = true)
    public List<Mesa> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Mesa findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con id: " + id));
    }

    @Transactional
    public Mesa save(Mesa mesa) {
        LugarAtencion lugar = lugarRepo.findById(mesa.getLugar().getIdLugar())
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado con id: " + mesa.getLugar().getIdLugar()));
        mesa.setLugar(lugar);
        return repository.save(mesa);
    }

    @Transactional
    public Mesa update(Integer id, Mesa mesa) {
        Mesa existing = findById(id);
        existing.setNombreMesa(mesa.getNombreMesa());
        if (mesa.getLugar() != null && mesa.getLugar().getIdLugar() != null) {
            LugarAtencion lugar = lugarRepo.findById(mesa.getLugar().getIdLugar())
                    .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado con id: " + mesa.getLugar().getIdLugar()));
            existing.setLugar(lugar);
        }
        existing.setCapacidad(mesa.getCapacidad());
        existing.setEstadoMesa(mesa.getEstadoMesa());
        return repository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Mesa existing = findById(id);
        existing.setEstadoMesa(false);
        repository.save(existing);
    }

    @Transactional(readOnly = true)
    public List<Mesa> findByLugar(Integer idLugar) {
        return repository.findByLugar_IdLugar(idLugar);
    }
}

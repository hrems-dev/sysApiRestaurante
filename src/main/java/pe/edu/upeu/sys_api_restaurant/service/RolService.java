package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.Rol;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.repository.RolRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService {

    private final RolRepository repository;

    // Obtiene todos los registros de Rol
    @Transactional(readOnly = true)
    public List<Rol> findAll() {
        return repository.findAll();
    }

    // Busca un Rol por su ID, lanza excepción si no existe
    @Transactional(readOnly = true)
    public Rol findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con id: " + id));
    }

    // Guarda un nuevo Rol
    @Transactional
    public Rol save(Rol rol) {
        return repository.save(rol);
    }

    // Actualiza un Rol existente buscándolo por ID y mapeando campos nuevos
    @Transactional
    public Rol update(Integer id, Rol rol) {
        Rol existing = findById(id);
        existing.setNombreRol(rol.getNombreRol());
        existing.setDescripcionRol(rol.getDescripcionRol());
        existing.setEstadoRol(rol.getEstadoRol());
        return repository.save(existing);
    }

    // Elimina un Rol por su ID
    @Transactional
    public void delete(Integer id) {
        Rol existing = findById(id);
        repository.delete(existing);
    }
}

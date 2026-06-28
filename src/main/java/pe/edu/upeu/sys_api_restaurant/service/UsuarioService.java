package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.repository.UsuarioRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;

    // Obtiene todos los registros de Usuario
    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    // Busca un Usuario por su ID, lanza excepción si no existe
    @Transactional(readOnly = true)
    public Usuario findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    // Guarda un nuevo Usuario
    @Transactional
    public Usuario save(Usuario usuario) {
        return repository.save(usuario);
    }

    // Actualiza un Usuario existente buscándolo por ID y mapeando campos nuevos
    @Transactional
    public Usuario update(Integer id, Usuario usuario) {
        Usuario existing = findById(id);
        existing.setRol(usuario.getRol());
        existing.setNombreUsuario(usuario.getNombreUsuario());
        existing.setNombres(usuario.getNombres());
        existing.setApellidos(usuario.getApellidos());
        existing.setEmail(usuario.getEmail());
        existing.setPasswordHash(usuario.getPasswordHash());
        existing.setTelefono(usuario.getTelefono());
        existing.setEstadoUsuario(usuario.getEstadoUsuario());
        return repository.save(existing);
    }

    // Elimina un Usuario por su ID
    @Transactional
    public void delete(Integer id) {
        Usuario existing = findById(id);
        repository.delete(existing);
    }
}

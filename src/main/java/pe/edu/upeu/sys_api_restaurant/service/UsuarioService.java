package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public List<Usuario> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        usuario.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
        return repository.save(usuario);
    }

    @Transactional
    public Usuario update(Integer id, Usuario usuario) {
        Usuario existing = findById(id);
        existing.setRol(usuario.getRol());
        existing.setNombreUsuario(usuario.getNombreUsuario());
        existing.setNombres(usuario.getNombres());
        existing.setApellidos(usuario.getApellidos());
        existing.setEmail(usuario.getEmail());
        existing.setTelefono(usuario.getTelefono());
        existing.setEstadoUsuario(usuario.getEstadoUsuario());
        if (usuario.getPasswordHash() != null && !usuario.getPasswordHash().isBlank()) {
            if (!usuario.getPasswordHash().startsWith("$2")) {
                existing.setPasswordHash(passwordEncoder.encode(usuario.getPasswordHash()));
            } else {
                existing.setPasswordHash(usuario.getPasswordHash());
            }
        }
        return repository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        Usuario existing = findById(id);
        repository.delete(existing);
    }
}

package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.Notificacion;
import pe.edu.upeu.sys_api_restaurant.mapper.NotificacionMapper;
import pe.edu.upeu.sys_api_restaurant.repository.NotificacionRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository repository;

    @Transactional(readOnly = true)
    public List<NotificacionResponse> listarPorUsuario(Integer idUsuario) {
        return repository.findByIdUsuarioOrderByFechaNotificacionDesc(idUsuario)
                .stream()
                .map(NotificacionMapper::toNotificacionResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<NotificacionResponse> obtenerNoLeidas(Integer idUsuario) {
        return repository.findByLeidoFalseAndIdUsuario(idUsuario)
                .stream()
                .map(NotificacionMapper::toNotificacionResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public long contarNoLeidas(Integer idUsuario) {
        return repository.findByLeidoFalseAndIdUsuario(idUsuario).size();
    }

    @Transactional
    public NotificacionResponse generarNotificacion(NotificacionRequest request) {
        Notificacion entity = NotificacionMapper.toNotificacion(request);
        return NotificacionMapper.toNotificacionResponse(repository.save(entity));
    }

    @Transactional
    public NotificacionResponse marcarLeida(Integer id) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion no encontrada con id: " + id));
        notificacion.setLeido(true);
        return NotificacionMapper.toNotificacionResponse(repository.save(notificacion));
    }

    @Transactional
    public void eliminar(Integer id) {
        Notificacion notificacion = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notificacion no encontrada con id: " + id));
        repository.delete(notificacion);
    }
}

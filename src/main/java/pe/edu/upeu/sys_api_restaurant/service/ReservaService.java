package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;
import pe.edu.upeu.sys_api_restaurant.dto.ReservaRequest;
import pe.edu.upeu.sys_api_restaurant.dto.ReservaResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Reserva;
import pe.edu.upeu.sys_api_restaurant.mapper.ReservaMapper;
import pe.edu.upeu.sys_api_restaurant.repository.ReservaRepository;
import pe.edu.upeu.sys_api_restaurant.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final LugarAtencionRepository lugarAtencionRepository;
    private final DisponibilidadReservaService disponibilidadService;

    @Transactional(readOnly = true)
    public List<ReservaResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(ReservaMapper::toReservaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservaResponse findById(Integer id) {
        return repository.findById(id)
                .map(ReservaMapper::toReservaResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> findByUsuario(Integer idUsuario) {
        return repository.findByUsuario_IdUsuario(idUsuario)
                .stream()
                .map(ReservaMapper::toReservaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservaResponse> findByLugar(Integer idLugar) {
        return repository.findByLugarAtencion_IdLugar(idLugar)
                .stream()
                .map(ReservaMapper::toReservaResponse)
                .toList();
    }

    @Transactional
    public ReservaResponse crearReserva(ReservaRequest request) {
        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.idUsuario()));
        LugarAtencion lugar = lugarAtencionRepository.findById(request.idLugar())
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado con id: " + request.idLugar()));

        if (!disponibilidadService.verificarDisponibilidad(request.idLugar(), request.fechaHoraReserva(), request.cantidadPersonas())) {
            throw new IllegalStateException("No hay disponibilidad para la fecha y lugar solicitados");
        }

        Reserva entity = ReservaMapper.toReserva(request);
        entity.setUsuario(usuario);
        entity.setLugarAtencion(lugar);
        return ReservaMapper.toReservaResponse(repository.save(entity));
    }

    @Transactional
    public ReservaResponse confirmarReserva(Integer id, BigDecimal adelanto) {
        Reserva entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        entity.setEstadoReserva("confirmada");
        if (adelanto != null) {
            entity.setAdelantoReserva(adelanto);
        }
        return ReservaMapper.toReservaResponse(repository.save(entity));
    }

    @Transactional
    public ReservaResponse cancelarReserva(Integer id) {
        Reserva entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        entity.setEstadoReserva("cancelada");
        return ReservaMapper.toReservaResponse(repository.save(entity));
    }

    @Transactional(readOnly = true)
    public boolean verificarDisponibilidad(Integer idLugar, LocalDateTime fechaHora) {
        return disponibilidadService.verificarDisponibilidad(idLugar, fechaHora, 1);
    }

    @Transactional
    public void delete(Integer id) {
        Reserva entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + id));
        repository.delete(entity);
    }
}

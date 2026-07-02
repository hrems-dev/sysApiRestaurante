package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;
import pe.edu.upeu.sys_api_restaurant.entity.Reserva;
import pe.edu.upeu.sys_api_restaurant.repository.ReservaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DisponibilidadReservaService {

    private final ReservaRepository reservaRepository;
    private final LugarAtencionRepository lugarAtencionRepository;

    public boolean verificarDisponibilidad(Integer idLugar, LocalDateTime fecha, int personas) {
        LugarAtencion lugar = lugarAtencionRepository.findById(idLugar)
                .orElseThrow(() -> new RuntimeException("Lugar no encontrado con id: " + idLugar));

        if (lugar.getCapacidadMaxima() != null && personas > lugar.getCapacidadMaxima()) {
            return false;
        }

        LocalDateTime inicio = fecha.withHour(0).withMinute(0).withSecond(0);
        LocalDateTime fin = fecha.withHour(23).withMinute(59).withSecond(59);

        List<Reserva> reservas = reservaRepository.findByFechaHoraReservaBetween(inicio, fin);
        long ocupadas = reservas.stream()
                .filter(r -> r.getLugarAtencion().getIdLugar().equals(idLugar)
                        && !"cancelada".equals(r.getEstadoReserva()))
                .count();

        return ocupadas < lugar.getCapacidadMaxima();
    }

    public List<LocalDateTime> horariosDisponibles(Integer idLugar, LocalDate fecha) {
        return List.of(
                fecha.atTime(LocalTime.of(12, 0)),
                fecha.atTime(LocalTime.of(13, 0)),
                fecha.atTime(LocalTime.of(14, 0)),
                fecha.atTime(LocalTime.of(19, 0)),
                fecha.atTime(LocalTime.of(20, 0)),
                fecha.atTime(LocalTime.of(21, 0))
        );
    }
}

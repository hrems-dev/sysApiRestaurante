package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.dto.ReporteReservasResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Reserva;
import pe.edu.upeu.sys_api_restaurant.repository.ReservaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteReservasService {

    private final ReservaRepository reservaRepository;

    @Transactional(readOnly = true)
    public ReporteReservasResponse generarReporteReservas(LocalDate inicio, LocalDate fin) {
        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime finDT = fin.atTime(23, 59, 59);

        Map<String, Integer> reservasPorEstado = reservaRepository
                .findByFechaHoraReservaBetween(inicioDT, finDT)
                .stream()
                .collect(Collectors.groupingBy(
                        Reserva::getEstadoReserva,
                        Collectors.summingInt(r -> 1)
                ));

        long total = reservasPorEstado.values().stream().mapToInt(Integer::intValue).sum();

        return new ReporteReservasResponse(
                inicio + " - " + fin,
                total,
                reservasPorEstado
        );
    }
}

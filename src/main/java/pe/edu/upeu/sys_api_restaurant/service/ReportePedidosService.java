package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;
import pe.edu.upeu.sys_api_restaurant.repository.PedidoRepository;
import pe.edu.upeu.sys_api_restaurant.dto.ReportePedidosResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportePedidosService {

    private final PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public ReportePedidosResponse generarReportePedidos(LocalDate inicio, LocalDate fin) {
        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime finDT = fin.atTime(23, 59, 59);

        Map<String, Integer> pedidosPorEstado = pedidoRepository.findAll().stream()
                .filter(p -> p.getFechaHoraPedido() != null
                        && !p.getFechaHoraPedido().isBefore(inicioDT)
                        && !p.getFechaHoraPedido().isAfter(finDT))
                .collect(Collectors.groupingBy(
                        Pedido::getEstadoPedido,
                        Collectors.summingInt(p -> 1)
                ));

        long total = pedidosPorEstado.values().stream().mapToInt(Integer::intValue).sum();

        return new ReportePedidosResponse(
                inicio + " - " + fin,
                total,
                pedidosPorEstado
        );
    }
}

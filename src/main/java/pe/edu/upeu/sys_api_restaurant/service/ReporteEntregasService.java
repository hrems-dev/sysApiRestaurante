package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;
import pe.edu.upeu.sys_api_restaurant.repository.PedidoRepository;
import pe.edu.upeu.sys_api_restaurant.dto.ReporteEntregasResponse;
import pe.edu.upeu.sys_api_restaurant.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteEntregasService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<ReporteEntregasResponse> generarReporteEntregas(LocalDate inicio, LocalDate fin) {
        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime finDT = fin.atTime(23, 59, 59);

        List<Pedido> entregas = pedidoRepository.findAll().stream()
                .filter(p -> "delivery".equals(p.getTipoPedido())
                        && "entregado".equals(p.getEstadoPedido())
                        && p.getFechaHoraPedido() != null
                        && !p.getFechaHoraPedido().isBefore(inicioDT)
                        && !p.getFechaHoraPedido().isAfter(finDT))
                .toList();

        Map<Integer, List<Pedido>> porRepartidor = entregas.stream()
                .filter(p -> p.getIdRepartidor() != null)
                .collect(Collectors.groupingBy(Pedido::getIdRepartidor));

        return porRepartidor.entrySet().stream()
                .map(entry -> {
                    Integer idRep = entry.getKey();
                    List<Pedido> pedidos = entry.getValue();
                    String nombre = usuarioRepository.findById(idRep)
                            .map(u -> u.getNombres() + " " + u.getApellidos())
                            .orElse("Desconocido");
                    BigDecimal tiempoPromedio = BigDecimal.ZERO;
                    if (!pedidos.isEmpty()) {
                        double avg = pedidos.stream()
                                .mapToLong(p -> Duration.between(p.getFechaHoraPedido(), LocalDateTime.now()).toMinutes())
                                .average()
                                .orElse(0);
                        tiempoPromedio = BigDecimal.valueOf(avg);
                    }
                    return new ReporteEntregasResponse(nombre, pedidos.size(), tiempoPromedio);
                })
                .toList();
    }
}

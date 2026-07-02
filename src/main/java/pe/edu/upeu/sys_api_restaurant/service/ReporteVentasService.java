package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.dto.ReporteVentasResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Venta;
import pe.edu.upeu.sys_api_restaurant.repository.VentaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReporteVentasService {

    private final VentaRepository ventaRepository;

    @Transactional(readOnly = true)
    public ReporteVentasResponse generarReporteVentas(LocalDate inicio, LocalDate fin) {
        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime finDT = fin.atTime(23, 59, 59);

        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(inicioDT, finDT);

        Map<String, BigDecimal> ventasPorDia = ventas.stream()
                .collect(Collectors.groupingBy(
                        v -> v.getFechaVenta().toLocalDate().toString(),
                        Collectors.mapping(Venta::getTotalVenta,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
                ));

        BigDecimal totalVentas = ventas.stream()
                .map(Venta::getTotalVenta)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ReporteVentasResponse(
                inicio + " - " + fin,
                totalVentas,
                ventas.size(),
                ventasPorDia
        );
    }
}

package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.dto.ReporteContableResponse;
import pe.edu.upeu.sys_api_restaurant.dto.VentaDiariaResponse;
import pe.edu.upeu.sys_api_restaurant.repository.PagoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.PedidoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.ReservaRepository;
import pe.edu.upeu.sys_api_restaurant.entity.Venta;
import pe.edu.upeu.sys_api_restaurant.repository.VentaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContabilidadService {

    private final VentaRepository ventaRepository;
    private final PagoRepository pagoRepository;
    private final PedidoRepository pedidoRepository;
    private final ReservaRepository reservaRepository;

    @Transactional(readOnly = true)
    public VentaDiariaResponse ventasDiarias(LocalDate fecha) {
        LocalDateTime inicio = fecha.atStartOfDay();
        LocalDateTime fin = fecha.atTime(23, 59, 59);

        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(inicio, fin);

        BigDecimal totalVentas = ventas.stream()
                .map(Venta::getTotalVenta)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new VentaDiariaResponse(
                fecha,
                totalVentas,
                ventas.size(),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
    }

    @Transactional(readOnly = true)
    public ReporteContableResponse resumenPeriodo(LocalDate inicio, LocalDate fin) {
        LocalDateTime inicioDT = inicio.atStartOfDay();
        LocalDateTime finDT = fin.atTime(23, 59, 59);

        List<Venta> ventas = ventaRepository.findByFechaVentaBetween(inicioDT, finDT);
        BigDecimal ingresos = ventas.stream()
                .map(Venta::getTotalVenta)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long cantidadReservas = reservaRepository.findByFechaHoraReservaBetween(inicioDT, finDT).size();
        long cantidadPedidos = pedidoRepository.findAll().stream()
                .filter(p -> p.getFechaHoraPedido() != null
                        && !p.getFechaHoraPedido().isBefore(inicioDT)
                        && !p.getFechaHoraPedido().isAfter(finDT))
                .count();

        return new ReporteContableResponse(
                inicio + " - " + fin,
                ingresos,
                BigDecimal.ZERO,
                ingresos,
                ventas.size(),
                (int) cantidadPedidos,
                (int) cantidadReservas
        );
    }
}

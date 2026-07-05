package pe.edu.upeu.sys_api_restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.ReporteEntregasResponse;
import pe.edu.upeu.sys_api_restaurant.dto.ReportePedidosResponse;
import pe.edu.upeu.sys_api_restaurant.dto.ReporteReservasResponse;
import pe.edu.upeu.sys_api_restaurant.dto.ReporteVentasResponse;
import pe.edu.upeu.sys_api_restaurant.service.ReporteEntregasService;
import pe.edu.upeu.sys_api_restaurant.service.ReportePedidosService;
import pe.edu.upeu.sys_api_restaurant.service.ReporteReservasService;
import pe.edu.upeu.sys_api_restaurant.service.ReporteVentasService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor

public class ReporteController {

    private final ReporteVentasService reporteVentasService;
    private final ReportePedidosService reportePedidosService;
    private final ReporteReservasService reporteReservasService;
    private final ReporteEntregasService reporteEntregasService;

    @GetMapping("/ventas")
    public ResponseEntity<ReporteVentasResponse> reporteVentas(@RequestParam("inicio") LocalDate inicio,
                                                                 @RequestParam("fin") LocalDate fin) {
        return ResponseEntity.ok(reporteVentasService.generarReporteVentas(inicio, fin));
    }

    @GetMapping("/pedidos")
    public ResponseEntity<ReportePedidosResponse> reportePedidos(@RequestParam("inicio") LocalDate inicio,
                                                                   @RequestParam("fin") LocalDate fin) {
        return ResponseEntity.ok(reportePedidosService.generarReportePedidos(inicio, fin));
    }

    @GetMapping("/reservas")
    public ResponseEntity<ReporteReservasResponse> reporteReservas(@RequestParam("inicio") LocalDate inicio,
                                                                     @RequestParam("fin") LocalDate fin) {
        return ResponseEntity.ok(reporteReservasService.generarReporteReservas(inicio, fin));
    }

    @GetMapping("/entregas")
    public ResponseEntity<List<ReporteEntregasResponse>> reporteEntregas(@RequestParam("inicio") LocalDate inicio,
                                                                           @RequestParam("fin") LocalDate fin) {
        return ResponseEntity.ok(reporteEntregasService.generarReporteEntregas(inicio, fin));
    }
}

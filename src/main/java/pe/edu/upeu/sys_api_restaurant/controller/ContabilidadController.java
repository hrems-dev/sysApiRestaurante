package pe.edu.upeu.sys_api_restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.ReporteContableResponse;
import pe.edu.upeu.sys_api_restaurant.dto.VentaDiariaResponse;
import pe.edu.upeu.sys_api_restaurant.service.ContabilidadService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/contabilidad")
@RequiredArgsConstructor
public class ContabilidadController {
    private final ContabilidadService service;

    @GetMapping("/ventas-diarias")
    public ResponseEntity<VentaDiariaResponse> ventasDiarias(@RequestParam("fecha") LocalDate fecha) {
        return ResponseEntity.ok(service.ventasDiarias(fecha));
    }

    @GetMapping("/resumen")
    public ResponseEntity<ReporteContableResponse> resumen(@RequestParam("inicio") LocalDate inicio,
                                                             @RequestParam("fin") LocalDate fin) {
        return ResponseEntity.ok(service.resumenPeriodo(inicio, fin));
    }
}

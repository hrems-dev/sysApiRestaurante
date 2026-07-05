package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.service.VentaService;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService service;


    @GetMapping
    public ResponseEntity<List<VentaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<VentaResponse> findByIdPedido(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(service.findByIdPedido(idPedido));
    }

    @PostMapping
    public ResponseEntity<VentaResponse> create(@Valid @RequestBody VentaRequest request) {
        return new ResponseEntity<>(service.generarVentaDesdePedido(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/cerrar")
    public ResponseEntity<VentaResponse> cerrarVenta(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cerrarVenta(id));
    }

    @PutMapping("/{id}/anular")
    public ResponseEntity<VentaResponse> anularVenta(@PathVariable Integer id) {
        return ResponseEntity.ok(service.anularVenta(id));
    }
}

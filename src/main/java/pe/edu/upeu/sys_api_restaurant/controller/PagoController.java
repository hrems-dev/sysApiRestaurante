package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.PagoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.PagoResponse;
import pe.edu.upeu.sys_api_restaurant.dto.ValidacionPagoRequest;
import pe.edu.upeu.sys_api_restaurant.service.PagoService;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;

    @GetMapping
    public ResponseEntity<List<PagoResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/codigo/{codigoPago}")
    public ResponseEntity<PagoResponse> findByCodigo(@PathVariable String codigoPago) {
        return ResponseEntity.ok(service.findByCodigo(codigoPago));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<PagoResponse>> findByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(service.findByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<PagoResponse> create(@Valid @RequestBody PagoRequest request) {
        return new ResponseEntity<>(service.registrarPago(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/validar")
    public ResponseEntity<PagoResponse> validarPago(@PathVariable Integer id,
                                                     @Valid @RequestBody ValidacionPagoRequest request) {
        return ResponseEntity.ok(service.validarPago(request.codigoPago(), request.estadoPago()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

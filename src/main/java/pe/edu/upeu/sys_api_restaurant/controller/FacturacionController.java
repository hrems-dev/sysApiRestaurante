package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.ComprobanteResponse;
import pe.edu.upeu.sys_api_restaurant.dto.FacturaRequest;
import pe.edu.upeu.sys_api_restaurant.dto.FacturaResponse;
import pe.edu.upeu.sys_api_restaurant.service.FacturacionService;

import java.util.List;


@RestController
@RequestMapping("/api/facturacion")
@RequiredArgsConstructor
public class FacturacionController {

    private final FacturacionService service;

    @GetMapping("/comprobantes")
    public ResponseEntity<List<FacturaResponse>> listarComprobantes() {
        return ResponseEntity.ok(service.listarComprobantes());
    }

    @GetMapping("/comprobantes/{id}")
    public ResponseEntity<ComprobanteResponse> obtenerComprobante(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerComprobante(id));
    }

    @PostMapping("/emitir")
    public ResponseEntity<FacturaResponse> emitirComprobante(@Valid @RequestBody FacturaRequest request) {
        return new ResponseEntity<>(service.emitirComprobante(request), HttpStatus.CREATED);
    }
}

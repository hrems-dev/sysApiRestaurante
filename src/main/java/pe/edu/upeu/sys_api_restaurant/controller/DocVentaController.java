package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.service.DocVentaService;

import java.util.List;


@RestController
@RequestMapping("/api/documentos-venta")
@RequiredArgsConstructor
public class DocVentaController {

    private final DocVentaService service;

    @GetMapping
    public ResponseEntity<List<DocVentaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocVentaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<DocVentaResponse> create(@Valid @RequestBody DocVentaRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }
}

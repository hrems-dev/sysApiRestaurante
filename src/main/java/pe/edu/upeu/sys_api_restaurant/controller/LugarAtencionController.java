package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.LugarAtencionRequest;
import pe.edu.upeu.sys_api_restaurant.dto.LugarAtencionResponse;
import pe.edu.upeu.sys_api_restaurant.service.LugarAtencionService;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
@RequiredArgsConstructor
public class LugarAtencionController {

    private final LugarAtencionService service;

    @GetMapping
    public ResponseEntity<List<LugarAtencionResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/activos")
    public ResponseEntity<List<LugarAtencionResponse>> findAllActive() {
        return ResponseEntity.ok(service.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LugarAtencionResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<LugarAtencionResponse>> findByTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.findByTipo(tipo));
    }

    @PostMapping
    public ResponseEntity<LugarAtencionResponse> create(@Valid @RequestBody LugarAtencionRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LugarAtencionResponse> update(@PathVariable Integer id, @Valid @RequestBody LugarAtencionRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

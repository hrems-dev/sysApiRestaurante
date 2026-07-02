package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.CategoriaProductoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.CategoriaProductoResponse;
import pe.edu.upeu.sys_api_restaurant.service.CategoriaProductoService;

import java.util.List;

@RestController
@RequestMapping("/api/categorias-producto")
@RequiredArgsConstructor
public class CategoriaProductoController {

    private final CategoriaProductoService service;

    @GetMapping
    public ResponseEntity<List<CategoriaProductoResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProductoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CategoriaProductoResponse> create(@Valid @RequestBody CategoriaProductoRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProductoResponse> update(@PathVariable Integer id, @Valid @RequestBody CategoriaProductoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

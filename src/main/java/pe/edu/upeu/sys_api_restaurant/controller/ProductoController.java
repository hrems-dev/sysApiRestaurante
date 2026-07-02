package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.ProductoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.ProductoResponse;
import pe.edu.upeu.sys_api_restaurant.service.ProductoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProductoResponse>> findByCategoria(@PathVariable Integer idCategoria) {
        return ResponseEntity.ok(service.findByCategoria(idCategoria));
    }

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<ProductoResponse>> findByNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(service.findByNombre(nombre));
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> create(@Valid @RequestBody ProductoRequest request) {
        return new ResponseEntity<>(service.create(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> update(@PathVariable Integer id, @Valid @RequestBody ProductoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductoResponse> updateStock(@PathVariable Integer id, @RequestBody Map<String, Integer> body) {
        Integer cantidad = body.get("cantidad");
        return ResponseEntity.ok(service.actualizarStock(id, cantidad));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

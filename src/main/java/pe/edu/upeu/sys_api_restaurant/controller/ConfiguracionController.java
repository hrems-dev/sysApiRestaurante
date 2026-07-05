package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.service.ConfiguracionService;

import java.util.List;

@RestController
@RequestMapping("/api/configuracion")
@RequiredArgsConstructor
public class ConfiguracionController {

    private final ConfiguracionService service;

    @GetMapping
    public ResponseEntity<List<ConfiguracionResponse>> obtenerTodas() {
        return ResponseEntity.ok(service.obtenerTodas());
    }
    @GetMapping("/{clave}")
    public ResponseEntity<ConfiguracionResponse> obtenerPorClave(@PathVariable String clave) {
        return ResponseEntity.ok(service.obtenerPorClave(clave));
    }

    @PostMapping
    public ResponseEntity<ConfiguracionResponse> crear(@Valid @RequestBody ConfiguracionRequest request) {
        return new ResponseEntity<>(service.crear(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConfiguracionResponse> actualizar(@PathVariable Integer id,
                                                            @Valid @RequestBody ConfiguracionRequest request) {
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.service.NotificacionService;

import java.util.List;


@RestController
@RequestMapping("/api/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {

    private final NotificacionService service;

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<NotificacionResponse>> listarPorUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/no-leidas")
    public ResponseEntity<List<NotificacionResponse>> obtenerNoLeidas(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.obtenerNoLeidas(idUsuario));
    }

    @GetMapping("/usuario/{idUsuario}/contar")
    public ResponseEntity<Long> contarNoLeidas(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.contarNoLeidas(idUsuario));
    }

    @PostMapping
    public ResponseEntity<NotificacionResponse> create(@Valid @RequestBody NotificacionRequest request) {
        return new ResponseEntity<>(service.generarNotificacion(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/leer")
    public ResponseEntity<NotificacionResponse> marcarLeida(@PathVariable Integer id) {
        return ResponseEntity.ok(service.marcarLeida(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

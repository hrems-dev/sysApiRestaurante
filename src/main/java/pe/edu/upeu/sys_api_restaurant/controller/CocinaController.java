package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.CambioEstadoCocinaRequest;
import pe.edu.upeu.sys_api_restaurant.dto.CocinaPedidoResponse;
import pe.edu.upeu.sys_api_restaurant.service.CocinaService;

import java.util.List;

@RestController
@RequestMapping("/api/cocina")
@RequiredArgsConstructor
public class CocinaController {

    private final CocinaService service;

    @GetMapping("/pendientes")
    public ResponseEntity<List<CocinaPedidoResponse>> listarPendientes() {
        return ResponseEntity.ok(service.listarPedidosPendientes());
    }
    @GetMapping("/preparacion")
    public ResponseEntity<List<CocinaPedidoResponse>> listarPreparacion() {
        return ResponseEntity.ok(service.listarPedidosEnPreparacion());
    }

    @GetMapping("/listos")
    public ResponseEntity<List<CocinaPedidoResponse>> listarListos() {
        return ResponseEntity.ok(service.listarPedidosListos());
    }

    @PutMapping("/{idPedido}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Integer idPedido,
                                                @Valid @RequestBody CambioEstadoCocinaRequest request) {
        service.cambiarEstado(idPedido, request.estado());
        return ResponseEntity.ok().build();
    }
}

package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.CambioEstadoCocinaRequest;
import pe.edu.upeu.sys_api_restaurant.dto.DeliveryRequest;
import pe.edu.upeu.sys_api_restaurant.dto.DeliveryResponse;
import pe.edu.upeu.sys_api_restaurant.dto.SeguimientoDeliveryResponse;
import pe.edu.upeu.sys_api_restaurant.service.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService service;

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> listarPedidosDelivery() {
        return ResponseEntity.ok(service.listarPedidosDelivery());
    }
    @GetMapping("/seguimiento/{idPedido}")
    public ResponseEntity<SeguimientoDeliveryResponse> obtenerSeguimiento(@PathVariable Integer idPedido) {
        return ResponseEntity.ok(service.obtenerSeguimiento(idPedido));
    }

    @PostMapping("/asignar")
    public ResponseEntity<DeliveryResponse> asignarRepartidor(@Valid @RequestBody DeliveryRequest request) {
        return new ResponseEntity<>(service.asignarRepartidor(request), HttpStatus.CREATED);
    }

    @PutMapping("/{idPedido}/estado")
    public ResponseEntity<Void> actualizarEstado(@PathVariable Integer idPedido,
                                                    @Valid @RequestBody CambioEstadoCocinaRequest request) {
        service.actualizarEstado(idPedido, request.estado());
        return ResponseEntity.ok().build();
    }
}

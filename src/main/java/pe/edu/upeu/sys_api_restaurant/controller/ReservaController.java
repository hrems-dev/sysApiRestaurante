package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.ReservaPagoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.ReservaPagoResponse;
import pe.edu.upeu.sys_api_restaurant.dto.ReservaRequest;
import pe.edu.upeu.sys_api_restaurant.dto.ReservaResponse;
import pe.edu.upeu.sys_api_restaurant.service.ReservaPagoService;
import pe.edu.upeu.sys_api_restaurant.service.ReservaService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService service;
    private final ReservaPagoService reservaPagoService;

    @GetMapping
    public ResponseEntity<List<ReservaResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponse> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<ReservaResponse>> findByUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(service.findByUsuario(idUsuario));
    }

    @GetMapping("/lugar/{idLugar}")
    public ResponseEntity<List<ReservaResponse>> findByLugar(@PathVariable Integer idLugar) {
        return ResponseEntity.ok(service.findByLugar(idLugar));
    }

    @GetMapping("/disponibilidad")
    public ResponseEntity<Boolean> verificarDisponibilidad(@RequestParam Integer lugar,
                                                             @RequestParam LocalDateTime fecha) {
        return ResponseEntity.ok(service.verificarDisponibilidad(lugar, fecha));
    }

    @PostMapping
    public ResponseEntity<ReservaResponse> create(@Valid @RequestBody ReservaRequest request) {
        return new ResponseEntity<>(service.crearReserva(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<ReservaResponse> confirmarReserva(@PathVariable Integer id,
                                                              @RequestBody Map<String, BigDecimal> body) {
        BigDecimal adelanto = body.getOrDefault("adelanto", BigDecimal.ZERO);
        return ResponseEntity.ok(service.confirmarReserva(id, adelanto));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponse> cancelarReserva(@PathVariable Integer id) {
        return ResponseEntity.ok(service.cancelarReserva(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pago")
    public ResponseEntity<ReservaPagoResponse> createPago(@Valid @RequestBody ReservaPagoRequest request) {
        return new ResponseEntity<>(reservaPagoService.create(request), HttpStatus.CREATED);
    }

    @GetMapping("/pago/reserva/{idReserva}")
    public ResponseEntity<List<ReservaPagoResponse>> findPagosByReserva(@PathVariable Integer idReserva) {
        return ResponseEntity.ok(reservaPagoService.findByReserva(idReserva));
    }
}

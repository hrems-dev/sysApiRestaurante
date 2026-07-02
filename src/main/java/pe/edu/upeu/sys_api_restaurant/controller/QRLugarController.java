package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.QRLugarRequest;
import pe.edu.upeu.sys_api_restaurant.dto.QRLugarResponse;
import pe.edu.upeu.sys_api_restaurant.service.QRLugarService;

@RestController
@RequestMapping("/api/qr")
@RequiredArgsConstructor
public class QRLugarController {

    private final QRLugarService service;

    @PostMapping("/generar")
    public ResponseEntity<QRLugarResponse> generarQR(@Valid @RequestBody QRLugarRequest request) {
        return new ResponseEntity<>(service.generarQR(request), HttpStatus.CREATED);
    }

    @GetMapping("/lugar/{idLugar}")
    public ResponseEntity<QRLugarResponse> obtenerQRPorLugar(@PathVariable Integer idLugar) {
        return ResponseEntity.ok(service.obtenerQRPorLugar(idLugar));
    }

    @GetMapping("/validar/{codigoQR}")
    public ResponseEntity<Boolean> validarQR(@PathVariable String codigoQR) {
        return ResponseEntity.ok(service.validarQR(codigoQR));
    }

    @PutMapping("/desactivar/{idQR}")
    public ResponseEntity<Void> desactivarQR(@PathVariable Integer idQR) {
        service.desactivarQR(idQR);
        return ResponseEntity.ok().build();
    }
}

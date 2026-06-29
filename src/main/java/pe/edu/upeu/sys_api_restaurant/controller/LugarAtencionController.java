package pe.edu.upeu.sys_api_restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.entity.QRLugar;
import pe.edu.upeu.sys_api_restaurant.service.LugarAtencionService;
import pe.edu.upeu.sys_api_restaurant.service.QRLugarService;

import java.util.List;

@RestController
@RequestMapping("/api/lugares")
@RequiredArgsConstructor
public class LugarAtencionController {

    private final LugarAtencionService lugarService;
    private final QRLugarService qrService;

    @GetMapping
    public ResponseEntity<List<LugarAtencion>> findAll() {
        return ResponseEntity.ok(lugarService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LugarAtencion> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(lugarService.findById(id));
    }

    @PostMapping
    public ResponseEntity<LugarAtencion> save(@RequestBody LugarAtencion lugar) {
        LugarAtencion saved = lugarService.save(lugar);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LugarAtencion> update(@PathVariable Integer id, @RequestBody LugarAtencion lugar) {
        return ResponseEntity.ok(lugarService.update(id, lugar));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        lugarService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/qr")
    public ResponseEntity<QRLugar> generarQR(@PathVariable Integer id) {
        LugarAtencion lugar = lugarService.findById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(qrService.generarParaLugar(lugar));
    }

    @GetMapping("/qr/{codigo}")
    public ResponseEntity<QRLugar> findByCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(qrService.findByCodigoQR(codigo));
    }
}

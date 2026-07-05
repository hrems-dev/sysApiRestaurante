package pe.edu.upeu.sys_api_restaurant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.entity.Mesa;
import pe.edu.upeu.sys_api_restaurant.service.MesaService;

import java.util.List;


@RestController
@RequestMapping("/api/mesas")
@RequiredArgsConstructor
public class MesaController {

    private final MesaService service;

    @GetMapping
    public ResponseEntity<List<Mesa>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mesa> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/lugar/{idLugar}")
    public ResponseEntity<List<Mesa>> findByLugar(@PathVariable Integer idLugar) {
        return ResponseEntity.ok(service.findByLugar(idLugar));
    }

    @PostMapping
    public ResponseEntity<Mesa> save(@RequestBody Mesa mesa) {
        return new ResponseEntity<>(service.save(mesa), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mesa> update(@PathVariable Integer id, @RequestBody Mesa mesa) {
        return ResponseEntity.ok(service.update(id, mesa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

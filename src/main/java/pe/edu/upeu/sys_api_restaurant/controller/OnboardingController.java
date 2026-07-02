package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.sys_api_restaurant.dto.EstadoModulosResponse;
import pe.edu.upeu.sys_api_restaurant.dto.ImportacionExcelResponse;
import pe.edu.upeu.sys_api_restaurant.dto.OnboardingRestauranteRequest;
import pe.edu.upeu.sys_api_restaurant.dto.OnboardingRestauranteResponse;
import pe.edu.upeu.sys_api_restaurant.service.ExcelImportService;
import pe.edu.upeu.sys_api_restaurant.service.OnboardingService;

@RestController
@RequestMapping("/api/onboarding")
@RequiredArgsConstructor
public class OnboardingController {

    private final OnboardingService onboardingService;
    private final ExcelImportService excelImportService;

    @PostMapping
    public ResponseEntity<OnboardingRestauranteResponse> crearRestaurante(
            @Valid @RequestBody OnboardingRestauranteRequest request) {
        OnboardingRestauranteResponse response = onboardingService.crearRestaurante(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/estado")
    public ResponseEntity<OnboardingRestauranteResponse> obtenerEstado() {
        OnboardingRestauranteResponse response = onboardingService.obtenerEstadoOnboarding();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/restaurante")
    public ResponseEntity<OnboardingRestauranteResponse> obtenerRestaurante() {
        OnboardingRestauranteResponse response = onboardingService.obtenerRestaurante();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/plantilla-excel")
    public ResponseEntity<Resource> descargarPlantilla() {
        try {
            byte[] contenido = excelImportService.generarPlantilla();
            ByteArrayResource resource = new ByteArrayResource(contenido);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=plantilla_datos_restaurante.xlsx")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/estado-modulos")
    public ResponseEntity<EstadoModulosResponse> obtenerEstadoModulos() {
        EstadoModulosResponse response = onboardingService.obtenerEstadoModulos();
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/importar-excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImportacionExcelResponse> importarExcel(@RequestParam("archivo") MultipartFile archivo) {
        if (archivo.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    ImportacionExcelResponse.builder()
                            .totalImportados(0)
                            .totalErrores(1)
                            .errores(java.util.List.of("El archivo está vacío"))
                            .mensaje("No se recibió ningún archivo")
                            .build()
            );
        }
        ImportacionExcelResponse response = excelImportService.importarExcel(archivo);
        return ResponseEntity.ok(response);
    }
}

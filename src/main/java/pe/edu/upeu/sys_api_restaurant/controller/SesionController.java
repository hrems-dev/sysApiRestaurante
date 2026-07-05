package pe.edu.upeu.sys_api_restaurant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.sys_api_restaurant.dto.ApiResponse;
import pe.edu.upeu.sys_api_restaurant.dto.LogoutRequest;
import pe.edu.upeu.sys_api_restaurant.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class SesionController {

    private final AuthService authService;

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse> logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request.token());
        return ResponseEntity.ok(new ApiResponse(200, "Sesión cerrada exitosamente", null));
    }
}

package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Size(min = 2, max = 80) String nombres,
        @NotBlank @Size(min = 2, max = 80) String apellidos,
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6) String password,
        String telefono,
        @NotBlank String rolNombre
) {}

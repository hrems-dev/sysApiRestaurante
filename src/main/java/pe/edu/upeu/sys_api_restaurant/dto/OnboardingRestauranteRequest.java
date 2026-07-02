package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnboardingRestauranteRequest {

    @NotBlank(message = "El nombre del restaurante es obligatorio")
    private String nombreRestaurante;

    private String ruc;

    private String direccion;

    private String telefono;

    private String email;

    private String logoUrl;
}

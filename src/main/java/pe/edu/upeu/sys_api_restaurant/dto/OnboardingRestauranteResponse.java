package pe.edu.upeu.sys_api_restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OnboardingRestauranteResponse {

    private Integer idRestaurante;
    private String nombreRestaurante;
    private String ruc;
    private String direccion;
    private String telefono;
    private String email;
    private String logoUrl;
    private Boolean estadoRestaurante;
    private String mensaje;
}

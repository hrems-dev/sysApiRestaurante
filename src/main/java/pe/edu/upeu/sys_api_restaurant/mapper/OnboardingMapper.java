package pe.edu.upeu.sys_api_restaurant.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.dto.OnboardingRestauranteResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Restaurante;

@Component
public class OnboardingMapper {

    public static OnboardingRestauranteResponse toResponse(Restaurante restaurante) {
        return OnboardingRestauranteResponse.builder()
                .idRestaurante(restaurante.getIdRestaurante())
                .nombreRestaurante(restaurante.getNombreRestaurante())
                .ruc(restaurante.getRuc())
                .direccion(restaurante.getDireccion())
                .telefono(restaurante.getTelefono())
                .email(restaurante.getEmail())
                .logoUrl(restaurante.getLogoUrl())
                .estadoRestaurante(restaurante.getEstadoRestaurante())
                .build();
    }

    public static Restaurante toEntity(OnboardingRestauranteResponse response) {
        return Restaurante.builder()
                .idRestaurante(response.getIdRestaurante())
                .nombreRestaurante(response.getNombreRestaurante())
                .ruc(response.getRuc())
                .direccion(response.getDireccion())
                .telefono(response.getTelefono())
                .email(response.getEmail())
                .logoUrl(response.getLogoUrl())
                .estadoRestaurante(response.getEstadoRestaurante())
                .build();
    }
}

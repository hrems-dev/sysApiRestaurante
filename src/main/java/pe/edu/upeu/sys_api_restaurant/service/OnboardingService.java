package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.dto.EstadoModulosResponse;
import pe.edu.upeu.sys_api_restaurant.dto.OnboardingRestauranteRequest;
import pe.edu.upeu.sys_api_restaurant.dto.OnboardingRestauranteResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Restaurante;
import pe.edu.upeu.sys_api_restaurant.mapper.OnboardingMapper;
import pe.edu.upeu.sys_api_restaurant.repository.CategoriaProductoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;
import pe.edu.upeu.sys_api_restaurant.repository.ProductoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.RestauranteRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OnboardingService {

    private final RestauranteRepository restauranteRepository;
    private final CategoriaProductoRepository categoriaRepo;
    private final ProductoRepository productoRepo;
    private final LugarAtencionRepository lugarRepo;

    @Transactional
    public OnboardingRestauranteResponse crearRestaurante(OnboardingRestauranteRequest request) {
        Restaurante restaurante = Restaurante.builder()
                .nombreRestaurante(request.getNombreRestaurante())
                .ruc(request.getRuc())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .logoUrl(request.getLogoUrl())
                .estadoRestaurante(true)
                .build();

        Restaurante saved = restauranteRepository.save(restaurante);
        OnboardingRestauranteResponse response = OnboardingMapper.toResponse(saved);
        response.setMensaje("Restaurante registrado exitosamente");
        return response;
    }

    @Transactional(readOnly = true)
    public OnboardingRestauranteResponse obtenerRestaurante() {
        Optional<Restaurante> optional = restauranteRepository.findAll().stream().findFirst();
        if (optional.isEmpty()) {
            return OnboardingRestauranteResponse.builder()
                    .mensaje("No hay restaurante registrado")
                    .build();
        }
        OnboardingRestauranteResponse response = OnboardingMapper.toResponse(optional.get());
        response.setMensaje("Restaurante encontrado");
        return response;
    }

    @Transactional(readOnly = true)
    public boolean existeConfiguracionInicial() {
        return restauranteRepository.count() > 0;
    }

    @Transactional(readOnly = true)
    public OnboardingRestauranteResponse obtenerEstadoOnboarding() {
        boolean completo = existeConfiguracionInicial();
        return OnboardingRestauranteResponse.builder()
                .estadoRestaurante(completo)
                .mensaje(completo ? "Onboarding completado" : "Onboarding pendiente")
                .build();
    }

    @Transactional(readOnly = true)
    public EstadoModulosResponse obtenerEstadoModulos() {
        boolean restauranteRegistrado = restauranteRepository.count() > 0;
        boolean datosBasicosRegistrados = categoriaRepo.count() > 0
                || productoRepo.count() > 0
                || lugarRepo.count() > 0;
        return EstadoModulosResponse.builder()
                .restauranteRegistrado(restauranteRegistrado)
                .datosBasicosRegistrados(datosBasicosRegistrados)
                .configuracionCompleta(restauranteRegistrado && datosBasicosRegistrados)
                .build();
    }
}

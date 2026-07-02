package pe.edu.upeu.sys_api_restaurant.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EstadoPedidoService {

    private static final Map<String, List<String>> TRANSICIONES = Map.of(
            "pendiente", List.of("aceptado", "cancelado"),
            "aceptado", List.of("en_preparacion", "cancelado"),
            "en_preparacion", List.of("en_camino", "cancelado"),
            "en_camino", List.of("entregado", "cancelado"),
            "entregado", List.of(),
            "cancelado", List.of()
    );

    public boolean validarTransicion(String estadoActual, String nuevoEstado) {
        List<String> siguientes = TRANSICIONES.get(estadoActual);
        return siguientes != null && siguientes.contains(nuevoEstado);
    }

    public List<String> estadosValidosSiguientes(String estadoActual) {
        return TRANSICIONES.getOrDefault(estadoActual, List.of());
    }
}

package pe.edu.upeu.sys_api_restaurant.service;

import org.springframework.stereotype.Service;

@Service
public class FlujoCocinaService {

    public boolean validarTransicion(String estadoActual, String nuevoEstado) {
        return switch (estadoActual) {
            case "pendiente" -> "aceptado".equals(nuevoEstado);
            case "aceptado" -> "en_preparacion".equals(nuevoEstado);
            case "en_preparacion" -> "entregado".equals(nuevoEstado);
            default -> false;
        };
    }
}

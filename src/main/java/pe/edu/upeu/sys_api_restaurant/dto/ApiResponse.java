package pe.edu.upeu.sys_api_restaurant.dto;

import java.time.LocalDateTime;

public record ApiResponse(
        int codigo,
        String mensaje,
        Object datos,
        LocalDateTime timestamp
) {
    public ApiResponse(int codigo, String mensaje, Object datos) {
        this(codigo, mensaje, datos, LocalDateTime.now());
    }
}

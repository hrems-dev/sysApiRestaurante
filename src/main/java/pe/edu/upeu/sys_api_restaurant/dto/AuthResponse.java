package pe.edu.upeu.sys_api_restaurant.dto;


public record AuthResponse(
        String token,
        String username,
        String rol,
        String mensaje
) {}

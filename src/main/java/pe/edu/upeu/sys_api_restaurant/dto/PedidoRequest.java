package pe.edu.upeu.sys_api_restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequest(
        @NotNull Integer idUsuario,
        Integer idLugar,
        @NotBlank String tipoPedido,
        String direccionEntrega,
        String observacionPedido,
        List<DetallePedidoRequest> detalles
) {}

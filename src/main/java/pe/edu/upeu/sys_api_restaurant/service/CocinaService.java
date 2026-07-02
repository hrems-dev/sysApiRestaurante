package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.BadRequestException;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.CocinaPedidoResponse;
import pe.edu.upeu.sys_api_restaurant.mapper.CocinaMapper;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;
import pe.edu.upeu.sys_api_restaurant.repository.PedidoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CocinaService {

    private final PedidoRepository pedidoRepository;
    private final FlujoCocinaService flujoCocinaService;

    @Transactional(readOnly = true)
    public List<CocinaPedidoResponse> listarPedidosPendientes() {
        return pedidoRepository.findByEstadoPedidoIn(List.of("pendiente", "aceptado"))
                .stream()
                .filter(Pedido::getPagado)
                .map(CocinaMapper::toCocinaPedidoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CocinaPedidoResponse> listarPedidosEnPreparacion() {
        return pedidoRepository.findByEstadoPedido("en_preparacion")
                .stream()
                .map(CocinaMapper::toCocinaPedidoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CocinaPedidoResponse> listarPedidosListos() {
        return pedidoRepository.findByEstadoPedido("entregado")
                .stream()
                .map(CocinaMapper::toCocinaPedidoResponse)
                .toList();
    }

    @Transactional
    public void cambiarEstado(Integer idPedido, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + idPedido));

        if (!flujoCocinaService.validarTransicion(pedido.getEstadoPedido(), nuevoEstado)) {
            throw new BadRequestException("Transicion no valida de " + pedido.getEstadoPedido() + " a " + nuevoEstado);
        }

        pedido.setEstadoPedido(nuevoEstado);
        pedidoRepository.save(pedido);
    }
}

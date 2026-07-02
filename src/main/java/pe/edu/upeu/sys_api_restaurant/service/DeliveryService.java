package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.DeliveryRequest;
import pe.edu.upeu.sys_api_restaurant.dto.DeliveryResponse;
import pe.edu.upeu.sys_api_restaurant.dto.SeguimientoDeliveryResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;
import pe.edu.upeu.sys_api_restaurant.repository.PedidoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.UsuarioRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final SeguimientoDeliveryService seguimientoService;

    @Transactional(readOnly = true)
    public List<DeliveryResponse> listarPedidosDelivery() {
        return pedidoRepository.findAll().stream()
                .filter(p -> "delivery".equals(p.getTipoPedido())
                        && List.of("en_preparacion", "en_camino", "entregado").contains(p.getEstadoPedido()))
                .map(this::toDeliveryResponse)
                .toList();
    }

    @Transactional
    public DeliveryResponse asignarRepartidor(DeliveryRequest request) {
        Pedido pedido = pedidoRepository.findById(request.idPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + request.idPedido()));

        Usuario repartidor = usuarioRepository.findById(request.idRepartidor())
                .orElseThrow(() -> new ResourceNotFoundException("Repartidor no encontrado con id: " + request.idRepartidor()));

        pedido.setIdRepartidor(request.idRepartidor());
        pedido.setDireccionEntrega(request.direccionEntrega());
        pedido.setEstadoPedido("en_preparacion");

        return toDeliveryResponse(pedidoRepository.save(pedido));
    }

    @Transactional
    public void actualizarEstado(Integer idPedido, String estado) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + idPedido));
        pedido.setEstadoPedido(estado);
        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public SeguimientoDeliveryResponse obtenerSeguimiento(Integer idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + idPedido));
        return new SeguimientoDeliveryResponse(
                pedido.getIdPedido(),
                pedido.getEstadoPedido(),
                pedido.getFechaHoraPedido(),
                seguimientoService.calcularTiempoTranscurrido(pedido.getFechaHoraPedido())
        );
    }

    private DeliveryResponse toDeliveryResponse(Pedido pedido) {
        String nombreRepartidor = null;
        if (pedido.getIdRepartidor() != null) {
            nombreRepartidor = usuarioRepository.findById(pedido.getIdRepartidor())
                    .map(u -> u.getNombres() + " " + u.getApellidos())
                    .orElse(null);
        }
        return new DeliveryResponse(
                pedido.getIdPedido(),
                nombreRepartidor,
                pedido.getDireccionEntrega(),
                pedido.getEstadoPedido(),
                pedido.getFechaHoraPedido()
        );
    }
}

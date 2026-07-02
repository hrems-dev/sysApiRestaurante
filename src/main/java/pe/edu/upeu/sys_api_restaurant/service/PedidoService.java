package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.Rol;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;
import pe.edu.upeu.sys_api_restaurant.repository.RolRepository;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.DetallePedido;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;
import pe.edu.upeu.sys_api_restaurant.mapper.PedidoMapper;
import pe.edu.upeu.sys_api_restaurant.repository.PedidoRepository;
import pe.edu.upeu.sys_api_restaurant.entity.Producto;
import pe.edu.upeu.sys_api_restaurant.repository.ProductoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.UsuarioRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final LugarAtencionRepository lugarAtencionRepository;
    private final ProductoRepository productoRepository;
    private final RolRepository rolRepository;
    private final EstadoPedidoService estadoPedidoService;

    @Transactional
    public PedidoResponse create(PedidoRequest request) {
        Usuario usuario;
        if (request.idUsuario() == null || request.idUsuario() == 0) {
            usuario = usuarioRepository.findByNombreUsuario("PUBLICO")
                    .orElseGet(() -> {
                        Rol rol = rolRepository.findByNombreRol("MESERO")
                                .orElseThrow(() -> new RuntimeException("Rol MESERO no encontrado"));
                        Usuario u = Usuario.builder()
                                .nombreUsuario("PUBLICO")
                                .nombres("Cliente")
                                .apellidos("Publico")
                                .passwordHash("")
                                .rol(rol)
                                .estadoUsuario(true)
                                .build();
                        return usuarioRepository.save(u);
                    });
        } else {
            usuario = usuarioRepository.findById(request.idUsuario())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + request.idUsuario()));
        }

        LugarAtencion lugar = null;
        if (request.idLugar() != null) {
            lugar = lugarAtencionRepository.findById(request.idLugar())
                    .orElseThrow(() -> new ResourceNotFoundException("LugarAtencion no encontrado con id: " + request.idLugar()));
        }

        Pedido pedido = Pedido.builder()
                .usuario(usuario)
                .lugarAtencion(lugar)
                .tipoPedido(request.tipoPedido())
                .estadoPedido("pendiente")
                .direccionEntrega(request.direccionEntrega())
                .observacionPedido(request.observacionPedido())
                .totalPedido(BigDecimal.ZERO)
                .pagado(false)
                .build();

        pedido = pedidoRepository.save(pedido);

        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedidoRequest detReq : request.detalles()) {
            Producto producto = productoRepository.findById(detReq.idProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + detReq.idProducto()));

            BigDecimal subtotal = detReq.precioUnitario().multiply(BigDecimal.valueOf(detReq.cantidad()));

            DetallePedido detalle = DetallePedido.builder()
                    .pedido(pedido)
                    .idProducto(detReq.idProducto())
                    .cantidad(detReq.cantidad())
                    .precioUnitario(detReq.precioUnitario())
                    .subtotal(subtotal)
                    .observacionDetalle(detReq.observacionDetalle())
                    .estadoDetalle(true)
                    .build();

            pedido.getDetalles().add(detalle);
            total = total.add(subtotal);
        }

        pedido.setTotalPedido(total);
        pedido = pedidoRepository.save(pedido);

        Pedido finalPedido = pedido;
        return new PedidoResponse(
                finalPedido.getIdPedido(),
                finalPedido.getUsuario().getNombres() + " " + finalPedido.getUsuario().getApellidos(),
                finalPedido.getLugarAtencion() != null ? finalPedido.getLugarAtencion().getNombreLugar() : null,
                finalPedido.getTipoPedido(),
                finalPedido.getEstadoPedido(),
                finalPedido.getDireccionEntrega(),
                finalPedido.getObservacionPedido(),
                finalPedido.getFechaHoraPedido(),
                finalPedido.getTotalPedido(),
                finalPedido.getPagado(),
                finalPedido.getDetalles().stream()
                        .map(d -> PedidoMapper.toDetallePedidoResponse(d,
                                productoRepository.findById(d.getIdProducto()).orElse(null)))
                        .toList()
        );
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> findAll() {
        return pedidoRepository.findAll().stream()
                .map(PedidoMapper::toPedidoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse findById(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        return PedidoMapper.toPedidoResponse(pedido);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> findByUsuario(Integer idUsuario) {
        return pedidoRepository.findByUsuario_IdUsuario(idUsuario).stream()
                .map(PedidoMapper::toPedidoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> findByEstado(String estado) {
        return pedidoRepository.findByEstadoPedido(estado).stream()
                .map(PedidoMapper::toPedidoResponse)
                .toList();
    }

    @Transactional
    public PedidoResponse cambiarEstado(Integer id, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));

        if (!estadoPedidoService.validarTransicion(pedido.getEstadoPedido(), nuevoEstado)) {
            throw new IllegalStateException("Transicion de estado invalida: " + pedido.getEstadoPedido() + " -> " + nuevoEstado);
        }

        pedido.setEstadoPedido(nuevoEstado);
        pedido = pedidoRepository.save(pedido);
        return PedidoMapper.toPedidoResponse(pedido);
    }

    @Transactional
    public PedidoResponse marcarPagado(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        pedido.setPagado(true);
        pedido = pedidoRepository.save(pedido);
        return PedidoMapper.toPedidoResponse(pedido);
    }

    @Transactional
    public void delete(Integer id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + id));
        pedidoRepository.delete(pedido);
    }
}

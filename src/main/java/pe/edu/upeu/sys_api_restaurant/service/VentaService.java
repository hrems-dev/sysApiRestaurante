package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.BadRequestException;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.entity.Pedido;
import pe.edu.upeu.sys_api_restaurant.repository.PedidoRepository;
import pe.edu.upeu.sys_api_restaurant.dto.*;
import pe.edu.upeu.sys_api_restaurant.entity.DocVenta;
import pe.edu.upeu.sys_api_restaurant.entity.Venta;
import pe.edu.upeu.sys_api_restaurant.mapper.VentaMapper;
import pe.edu.upeu.sys_api_restaurant.repository.DocVentaRepository;
import pe.edu.upeu.sys_api_restaurant.repository.VentaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DocVentaRepository docVentaRepository;
    private final DocVentaService docVentaService;
    private final PedidoRepository pedidoRepository;

    @Transactional(readOnly = true)
    public List<VentaResponse> findAll() {
        return ventaRepository.findAll()
                .stream()
                .map(VentaMapper::toVentaResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public VentaResponse findById(Integer id) {
        return ventaRepository.findById(id)
                .map(VentaMapper::toVentaResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));
    }

    @Transactional(readOnly = true)
    public VentaResponse findByIdPedido(Integer idPedido) {
        return ventaRepository.findByIdPedido(idPedido)
                .map(VentaMapper::toVentaResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada para pedido: " + idPedido));
    }

    @Transactional
    public VentaResponse generarVentaDesdePedido(VentaRequest request) {
        Pedido pedido = pedidoRepository.findById(request.idPedido())
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con id: " + request.idPedido()));

        if (!pedido.getPagado()) {
            throw new BadRequestException("El pedido " + request.idPedido() + " no ha sido pagado");
        }

        if (ventaRepository.findByIdPedido(request.idPedido()).isPresent()) {
            throw new BadRequestException("Ya existe una venta generada para el pedido " + request.idPedido());
        }

        String correlativo = docVentaService.generarCorrelativo(request.tipoDocumento());
        String[] partes = correlativo.split("-", 2);
        String serie = partes[0];
        String numero = partes[1];

        DocVenta docVenta = DocVenta.builder()
                .tipoDocumento(request.tipoDocumento())
                .serie(serie)
                .numero(numero)
                .estadoDocumento(true)
                .build();
        docVenta = docVentaRepository.save(docVenta);

        Venta venta = Venta.builder()
                .idPedido(request.idPedido())
                .idPago(request.idPago())
                .docVenta(docVenta)
                .totalVenta(pedido.getTotalPedido())
                .estadoVenta("cerrada")
                .build();
        venta = ventaRepository.save(venta);

        return VentaMapper.toVentaResponse(venta);
    }

    @Transactional
    public VentaResponse cerrarVenta(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));
        venta.setEstadoVenta("cerrada");
        return VentaMapper.toVentaResponse(ventaRepository.save(venta));
    }

    @Transactional
    public VentaResponse anularVenta(Integer id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con id: " + id));
        venta.setEstadoVenta("anulada");
        return VentaMapper.toVentaResponse(ventaRepository.save(venta));
    }
}

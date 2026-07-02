package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.dto.DetallePedidoResponse;
import pe.edu.upeu.sys_api_restaurant.mapper.PedidoMapper;
import pe.edu.upeu.sys_api_restaurant.repository.DetallePedidoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetallePedidoService {

    private final DetallePedidoRepository repository;

    @Transactional(readOnly = true)
    public List<DetallePedidoResponse> findByIdPedido(Integer idPedido) {
        return repository.findByPedido_IdPedido(idPedido).stream()
                .map(PedidoMapper::toDetallePedidoResponse)
                .toList();
    }
}

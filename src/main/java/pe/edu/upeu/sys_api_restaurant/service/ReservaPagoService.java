package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.entity.Pago;
import pe.edu.upeu.sys_api_restaurant.repository.PagoRepository;
import pe.edu.upeu.sys_api_restaurant.dto.ReservaPagoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.ReservaPagoResponse;
import pe.edu.upeu.sys_api_restaurant.entity.Reserva;
import pe.edu.upeu.sys_api_restaurant.entity.ReservaPago;
import pe.edu.upeu.sys_api_restaurant.mapper.ReservaMapper;
import pe.edu.upeu.sys_api_restaurant.repository.ReservaPagoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.ReservaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaPagoService {

    private final ReservaPagoRepository repository;
    private final ReservaRepository reservaRepository;
    private final PagoRepository pagoRepository;

    @Transactional(readOnly = true)
    public List<ReservaPagoResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(ReservaMapper::toReservaPagoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ReservaPagoResponse findById(Integer id) {
        return repository.findById(id)
                .map(ReservaMapper::toReservaPagoResponse)
                .orElseThrow(() -> new ResourceNotFoundException("ReservaPago no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ReservaPagoResponse> findByReserva(Integer idReserva) {
        return repository.findByReserva_IdReserva(idReserva)
                .stream()
                .map(ReservaMapper::toReservaPagoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ReservaPagoResponse> findByPago(Integer idPago) {
        return repository.findByPago_IdPago(idPago)
                .stream()
                .map(ReservaMapper::toReservaPagoResponse)
                .toList();
    }

    @Transactional
    public ReservaPagoResponse create(ReservaPagoRequest request) {
        Reserva reserva = reservaRepository.findById(request.idReserva())
                .orElseThrow(() -> new ResourceNotFoundException("Reserva no encontrada con id: " + request.idReserva()));
        Pago pago = pagoRepository.findById(request.idPago())
                .orElseThrow(() -> new ResourceNotFoundException("Pago no encontrado con id: " + request.idPago()));

        ReservaPago entity = ReservaMapper.toReservaPago(request);
        entity.setReserva(reserva);
        entity.setPago(pago);
        return ReservaMapper.toReservaPagoResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Integer id) {
        ReservaPago entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ReservaPago no encontrado con id: " + id));
        repository.delete(entity);
    }
}

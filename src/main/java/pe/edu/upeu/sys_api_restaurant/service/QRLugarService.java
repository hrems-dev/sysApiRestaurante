package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.entity.QRLugar;
import pe.edu.upeu.sys_api_restaurant.exception.BadRequestException;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.repository.QRLugarRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QRLugarService {

    private final QRLugarRepository repository;

    @Transactional(readOnly = true)
    public List<QRLugar> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public QRLugar findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("QR no encontrado con id: " + id));
    }

    @Transactional
    public QRLugar generarParaLugar(LugarAtencion lugar) {
        if (lugar == null || lugar.getIdLugar() == null) {
            throw new BadRequestException("El lugar debe existir para generar el QR");
        }

        repository.findByLugarAtencionIdLugar(lugar.getIdLugar()).ifPresent(existing -> {
            throw new BadRequestException("Ya existe un QR asociado a este lugar");
        });

        String codigo = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        QRLugar qr = QRLugar.builder()
                .lugarAtencion(lugar)
                .codigoQR(codigo)
                .urlQR("http://localhost:8080/api/qr/validate/" + codigo)
                .fechaGeneracion(LocalDateTime.now())
                .estadoQR(true)
                .build();

        return repository.save(qr);
    }

    @Transactional(readOnly = true)
    public QRLugar findByCodigoQR(String codigoQR) {
        return repository.findByCodigoQR(codigoQR)
                .orElseThrow(() -> new ResourceNotFoundException("QR no encontrado"));
    }
}

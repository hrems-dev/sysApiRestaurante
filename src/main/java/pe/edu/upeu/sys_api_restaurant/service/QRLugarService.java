package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.QRLugarRequest;
import pe.edu.upeu.sys_api_restaurant.dto.QRLugarResponse;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.entity.QRLugar;
import pe.edu.upeu.sys_api_restaurant.mapper.LugarAtencionMapper;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;
import pe.edu.upeu.sys_api_restaurant.repository.QRLugarRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QRLugarService {

    private final QRLugarRepository qrLugarRepository;
    private final LugarAtencionRepository lugarAtencionRepository;

    @Transactional
    public QRLugarResponse generarQR(QRLugarRequest request) {
        LugarAtencion lugar = lugarAtencionRepository.findById(request.idLugar())
                .orElseThrow(() -> new ResourceNotFoundException("LugarAtencion no encontrado con id: " + request.idLugar()));

        QRLugar entity = QRLugar.builder()
                .lugarAtencion(lugar)
                .codigoQR(request.codigoQR())
                .urlQR(request.urlQR())
                .fechaGeneracion(LocalDateTime.now())
                .estadoQR(true)
                .build();
        return LugarAtencionMapper.toQRLugarResponse(qrLugarRepository.save(entity));
    }

    @Transactional(readOnly = true)
    public QRLugarResponse obtenerQRPorLugar(Integer idLugar) {
        QRLugar entity = qrLugarRepository.findByLugarAtencion_IdLugar(idLugar)
                .orElseThrow(() -> new ResourceNotFoundException("QR no encontrado para el lugar con id: " + idLugar));
        return LugarAtencionMapper.toQRLugarResponse(entity);
    }

    @Transactional(readOnly = true)
    public boolean validarQR(String codigoQR) {
        return qrLugarRepository.findByCodigoQR(codigoQR)
                .map(QRLugar::getEstadoQR)
                .orElse(false);
    }

    @Transactional
    public void desactivarQR(Integer idQR) {
        QRLugar entity = qrLugarRepository.findById(idQR)
                .orElseThrow(() -> new ResourceNotFoundException("QR no encontrado con id: " + idQR));
        entity.setEstadoQR(false);
        qrLugarRepository.save(entity);
    }
}

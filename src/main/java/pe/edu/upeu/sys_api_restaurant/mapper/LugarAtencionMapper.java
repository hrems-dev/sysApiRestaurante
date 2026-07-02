package pe.edu.upeu.sys_api_restaurant.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.dto.LugarAtencionResponse;
import pe.edu.upeu.sys_api_restaurant.dto.QRLugarResponse;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.entity.QRLugar;

@Component
public class LugarAtencionMapper {

    public static LugarAtencionResponse toLugarAtencionResponse(LugarAtencion entity) {
        return new LugarAtencionResponse(
                entity.getIdLugar(),
                entity.getNombreLugar(),
                entity.getTipoLugar(),
                entity.getDireccion(),
                entity.getCapacidadMaxima(),
                entity.getEstadoLugar(),
                entity.getObservacion()
        );
    }

    public static QRLugarResponse toQRLugarResponse(QRLugar entity) {
        return new QRLugarResponse(
                entity.getIdQR(),
                entity.getLugarAtencion().getIdLugar(),
                entity.getLugarAtencion().getNombreLugar(),
                entity.getCodigoQR(),
                entity.getUrlQR(),
                entity.getFechaGeneracion(),
                entity.getEstadoQR()
        );
    }
}

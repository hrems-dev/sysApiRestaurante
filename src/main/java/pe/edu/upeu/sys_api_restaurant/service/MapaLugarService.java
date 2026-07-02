package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.dto.LugarAtencionResponse;
import pe.edu.upeu.sys_api_restaurant.mapper.LugarAtencionMapper;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MapaLugarService {

    private final LugarAtencionRepository repository;

    @Transactional(readOnly = true)
    public Map<String, List<LugarAtencionResponse>> obtenerMapaCompleto() {
        Map<String, List<LugarAtencionResponse>> mapa = new LinkedHashMap<>();
        repository.findByEstadoLugarTrue().forEach(entity -> {
            String tipo = entity.getTipoLugar();
            mapa.computeIfAbsent(tipo, k -> new ArrayList<>());
            mapa.get(tipo).add(LugarAtencionMapper.toLugarAtencionResponse(entity));
        });
        return mapa;
    }
}

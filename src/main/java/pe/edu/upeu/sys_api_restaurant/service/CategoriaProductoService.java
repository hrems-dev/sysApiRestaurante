package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.CategoriaProductoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.CategoriaProductoResponse;
import pe.edu.upeu.sys_api_restaurant.entity.CategoriaProducto;
import pe.edu.upeu.sys_api_restaurant.mapper.ProductoMapper;
import pe.edu.upeu.sys_api_restaurant.repository.CategoriaProductoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaProductoService {

    private final CategoriaProductoRepository repository;

    @Transactional(readOnly = true)
    public List<CategoriaProductoResponse> findAll() {
        return repository.findAll().stream()
                .map(ProductoMapper::toCategoriaProductoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CategoriaProductoResponse> findAllActive() {
        return repository.findByEstadoCategoriaTrue().stream()
                .map(ProductoMapper::toCategoriaProductoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoriaProductoResponse findById(Integer id) {
        CategoriaProducto entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaProducto no encontrada con id: " + id));
        return ProductoMapper.toCategoriaProductoResponse(entity);
    }

    @Transactional
    public CategoriaProductoResponse create(CategoriaProductoRequest request) {
        CategoriaProducto entity = CategoriaProducto.builder()
                .nombreCategoria(request.nombreCategoria())
                .descripcionCategoria(request.descripcionCategoria())
                .estadoCategoria(true)
                .build();
        return ProductoMapper.toCategoriaProductoResponse(repository.save(entity));
    }

    @Transactional
    public CategoriaProductoResponse update(Integer id, CategoriaProductoRequest request) {
        CategoriaProducto existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaProducto no encontrada con id: " + id));
        existing.setNombreCategoria(request.nombreCategoria());
        existing.setDescripcionCategoria(request.descripcionCategoria());
        return ProductoMapper.toCategoriaProductoResponse(repository.save(existing));
    }

    @Transactional
    public void delete(Integer id) {
        CategoriaProducto existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaProducto no encontrada con id: " + id));
        existing.setEstadoCategoria(false);
        repository.save(existing);
    }
}

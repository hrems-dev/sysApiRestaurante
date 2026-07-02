package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.sys_api_restaurant.exception.ResourceNotFoundException;
import pe.edu.upeu.sys_api_restaurant.dto.ProductoRequest;
import pe.edu.upeu.sys_api_restaurant.dto.ProductoResponse;
import pe.edu.upeu.sys_api_restaurant.entity.CategoriaProducto;
import pe.edu.upeu.sys_api_restaurant.entity.Producto;
import pe.edu.upeu.sys_api_restaurant.mapper.ProductoMapper;
import pe.edu.upeu.sys_api_restaurant.repository.CategoriaProductoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.ProductoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository repository;
    private final CategoriaProductoRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ProductoResponse> findAll() {
        return repository.findAll().stream()
                .map(ProductoMapper::toProductoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> findAllActive() {
        return repository.findByEstadoProductoTrue().stream()
                .map(ProductoMapper::toProductoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProductoResponse findById(Integer id) {
        Producto entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        return ProductoMapper.toProductoResponse(entity);
    }

    @Transactional
    public ProductoResponse create(ProductoRequest request) {
        CategoriaProducto categoria = categoriaRepository.findById(request.idCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaProducto no encontrada con id: " + request.idCategoria()));
        Producto entity = Producto.builder()
                .categoria(categoria)
                .nombreProducto(request.nombreProducto())
                .descripcionProducto(request.descripcionProducto())
                .precioProducto(request.precioProducto())
                .stockProducto(request.stockProducto() != null ? request.stockProducto() : 0)
                .imagenProducto(request.imagenProducto())
                .estadoProducto(true)
                .build();
        return ProductoMapper.toProductoResponse(repository.save(entity));
    }

    @Transactional
    public ProductoResponse update(Integer id, ProductoRequest request) {
        Producto existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        CategoriaProducto categoria = categoriaRepository.findById(request.idCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("CategoriaProducto no encontrada con id: " + request.idCategoria()));
        existing.setCategoria(categoria);
        existing.setNombreProducto(request.nombreProducto());
        existing.setDescripcionProducto(request.descripcionProducto());
        existing.setPrecioProducto(request.precioProducto());
        existing.setStockProducto(request.stockProducto() != null ? request.stockProducto() : 0);
        existing.setImagenProducto(request.imagenProducto());
        return ProductoMapper.toProductoResponse(repository.save(existing));
    }

    @Transactional
    public void delete(Integer id) {
        Producto existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        existing.setEstadoProducto(false);
        repository.save(existing);
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> findByCategoria(Integer idCategoria) {
        return repository.findByCategoria_IdCategoria(idCategoria).stream()
                .map(ProductoMapper::toProductoResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ProductoResponse> findByNombre(String nombre) {
        return repository.findByNombreProductoContainingIgnoreCase(nombre).stream()
                .map(ProductoMapper::toProductoResponse)
                .toList();
    }

    @Transactional
    public ProductoResponse actualizarStock(Integer id, Integer cantidad) {
        Producto existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con id: " + id));
        existing.setStockProducto(cantidad);
        return ProductoMapper.toProductoResponse(repository.save(existing));
    }
}

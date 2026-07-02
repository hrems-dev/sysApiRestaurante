package pe.edu.upeu.sys_api_restaurant.mapper;

import org.springframework.stereotype.Component;
import pe.edu.upeu.sys_api_restaurant.dto.CategoriaProductoResponse;
import pe.edu.upeu.sys_api_restaurant.dto.ProductoResponse;
import pe.edu.upeu.sys_api_restaurant.entity.CategoriaProducto;
import pe.edu.upeu.sys_api_restaurant.entity.Producto;

@Component
public class ProductoMapper {

    public static CategoriaProductoResponse toCategoriaProductoResponse(CategoriaProducto entity) {
        return new CategoriaProductoResponse(
                entity.getIdCategoria(),
                entity.getNombreCategoria(),
                entity.getDescripcionCategoria(),
                entity.getEstadoCategoria()
        );
    }

    public static ProductoResponse toProductoResponse(Producto entity) {
        return new ProductoResponse(
                entity.getIdProducto(),
                entity.getCategoria().getIdCategoria(),
                entity.getCategoria().getNombreCategoria(),
                entity.getNombreProducto(),
                entity.getDescripcionProducto(),
                entity.getPrecioProducto(),
                entity.getStockProducto(),
                entity.getImagenProducto(),
                entity.getEstadoProducto()
        );
    }
}

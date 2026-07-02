package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "Producto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idCategoria", nullable = false)
    private CategoriaProducto categoria;

    @Column(nullable = false, length = 100)
    private String nombreProducto;

    @Column(length = 255)
    private String descripcionProducto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precioProducto;

    @Column(nullable = false)
    private Integer stockProducto;

    @Column(length = 255)
    private String imagenProducto;

    @Column(nullable = false)
    private Boolean estadoProducto;
}

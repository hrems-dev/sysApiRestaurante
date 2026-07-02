package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CategoriaProducto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategoria;

    @Column(nullable = false, length = 80, unique = true)
    private String nombreCategoria;

    @Column(length = 150)
    private String descripcionCategoria;

    @Column(nullable = false)
    private Boolean estadoCategoria;
}

package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "MetodoPago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class MetodoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idMetodoPago;

    @Column(nullable = false, length = 60, unique = true)
    private String nombreMetodo;

    @Column(length = 150)
    private String descripcionMetodo;

    @Column(nullable = false)
    private Boolean estadoMetodo;
}

package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Configuracion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Configuracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idConfig;

    @Column(nullable = false, length = 100, unique = true)
    private String clave;

    @Column(nullable = false, length = 255)
    private String valor;

    @Column(length = 255)
    private String descripcion;

    @Column(nullable = false)
    private Boolean estadoConfig;
}

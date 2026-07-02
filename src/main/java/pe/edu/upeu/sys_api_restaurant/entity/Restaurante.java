package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Restaurante")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idRestaurante;

    @Column(nullable = false, length = 150)
    private String nombreRestaurante;

    @Column(length = 11, unique = true)
    private String ruc;

    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(length = 100)
    private String email;

    @Column(length = 255)
    private String logoUrl;

    @Column(nullable = false)
    @Builder.Default
    private Boolean estadoRestaurante = true;
}

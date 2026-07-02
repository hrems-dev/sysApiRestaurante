package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Mesa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMesa;

    @Column(nullable = false, length = 50)
    private String nombreMesa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLugar", nullable = false)
    private LugarAtencion lugar;

    @Column(nullable = false)
    private Integer capacidad;

    @Column(nullable = false)
    private Boolean estadoMesa;
}

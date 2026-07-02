package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "LugarAtencion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LugarAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLugar;

    @Column(nullable = false, length = 80)
    private String nombreLugar;

    @Column(nullable = false, length = 20)
    private String tipoLugar;

    @Column(length = 200)
    private String direccion;

    private Integer capacidadMaxima;

    @Column(nullable = false)
    private Boolean estadoLugar;

    @Column(length = 255)
    private String observacion;
}

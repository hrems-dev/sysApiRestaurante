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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class LugarAtencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idLugar;

    @Column(nullable = false, length = 80)
    private String nombreLugar;

    @Column(nullable = false, length = 30)
    private String tipoLugar;

    @Column(length = 200)
    private String direccion;

    @Column
    private Integer capacidadMaxima;

    @Column(nullable = false)
    private Boolean estadoLugar;

    @Column(length = 255)
    private String observacion;

    @OneToOne(mappedBy = "lugarAtencion", cascade = CascadeType.ALL, orphanRemoval = true)
    private QRLugar qrLugar;

    @PrePersist
    protected void onCreate() {
        if (estadoLugar == null) {
            estadoLugar = true;
        }
    }
}

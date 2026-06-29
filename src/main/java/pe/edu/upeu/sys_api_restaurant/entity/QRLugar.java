package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "QRLugar")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class QRLugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idQR;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idLugar", nullable = false, unique = true)
    private LugarAtencion lugarAtencion;

    @Column(nullable = false, length = 255, unique = true)
    private String codigoQR;

    @Column(length = 255)
    private String urlQR;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    @Column
    private LocalDateTime fechaCaducidad;

    @Column(nullable = false)
    private boolean estadoQR;

    @PrePersist
    protected void onCreate() {
        if (fechaGeneracion == null) {
            fechaGeneracion = LocalDateTime.now();
        }
        if (estadoQR) {
            fechaCaducidad = null;
        }
    }
}

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
public class QRLugar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idQR;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "idLugar", nullable = false)
    private LugarAtencion lugarAtencion;

    @Column(nullable = false, unique = true, length = 255)
    private String codigoQR;

    @Column(length = 255)
    private String urlQR;

    @Column(nullable = false)
    private LocalDateTime fechaGeneracion;

    private LocalDateTime fechaCaducidad;

    @Column(nullable = false)
    private Boolean estadoQR;

    @PrePersist
    public void prePersist() {
        this.fechaGeneracion = LocalDateTime.now();
    }
}

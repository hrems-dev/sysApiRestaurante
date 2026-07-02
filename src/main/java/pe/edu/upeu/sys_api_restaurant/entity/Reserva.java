package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reserva")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idReserva;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idLugar", nullable = false)
    private LugarAtencion lugarAtencion;

    @Column(nullable = false)
    private LocalDateTime fechaHoraReserva;

    @Column(nullable = false)
    private Integer cantidadPersonas;

    @Column(nullable = false, length = 20)
    private String estadoReserva;

    @Column(precision = 10, scale = 2)
    private BigDecimal adelantoReserva;

    @Column(length = 255)
    private String observacionReserva;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estadoReserva == null) {
            estadoReserva = "pendiente";
        }
        if (adelantoReserva == null) {
            adelantoReserva = BigDecimal.ZERO;
        }
    }
}

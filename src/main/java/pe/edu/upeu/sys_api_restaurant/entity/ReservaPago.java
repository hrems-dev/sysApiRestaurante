package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.upeu.sys_api_restaurant.entity.Pago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ReservaPago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ReservaPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idReservaPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idReserva", nullable = false)
    private Reserva reserva;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idPago", nullable = false)
    private Pago pago;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoAplicado;

    @Column(nullable = false, length = 20)
    private String estadoPagoReserva;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaAplicacion;

    @PrePersist
    protected void onCreate() {
        fechaAplicacion = LocalDateTime.now();
        if (estadoPagoReserva == null) {
            estadoPagoReserva = "pendiente";
        }
    }
}

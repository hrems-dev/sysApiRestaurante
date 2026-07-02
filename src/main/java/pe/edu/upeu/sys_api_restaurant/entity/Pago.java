package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.upeu.sys_api_restaurant.entity.MetodoPago;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Pago")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idMetodoPago", nullable = false)
    private MetodoPago metodoPago;

    @Column(nullable = false, length = 100, unique = true)
    private String codigoPago;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPago;

    @Column(nullable = false, length = 20)
    private String estadoPago;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaPago;

    @Column(length = 120)
    private String referenciaPago;

    private Integer validadoPorUsuario;

    @PrePersist
    protected void onCreate() {
        fechaPago = LocalDateTime.now();
        if (estadoPago == null) {
            estadoPago = "pendiente";
        }
    }
}

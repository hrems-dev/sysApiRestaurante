package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Venta")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idVenta;

    @Column(nullable = false, unique = true)
    private Integer idPedido;

    @Column(nullable = false)
    private Integer idPago;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idDocVenta", nullable = false)
    private DocVenta docVenta;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalVenta;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaVenta;

    @Column(nullable = false, length = 20)
    private String estadoVenta;

    @PrePersist
    protected void onCreate() {
        fechaVenta = LocalDateTime.now();
        if (estadoVenta == null) estadoVenta = "cerrada";
    }
}

package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import pe.edu.upeu.sys_api_restaurant.entity.Usuario;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pedido")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPedido;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idLugar")
    private LugarAtencion lugarAtencion;

    @Column(name = "idRepartidor")
    private Integer idRepartidor;

    @Column(nullable = false, length = 20)
    private String tipoPedido;

    @Column(nullable = false, length = 20)
    private String estadoPedido;

    @Column(length = 255)
    private String direccionEntrega;

    @Column(length = 255)
    private String observacionPedido;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaHoraPedido;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPedido;

    @Column(nullable = false)
    private Boolean pagado;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<DetallePedido> detalles = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        fechaHoraPedido = LocalDateTime.now();
        if (totalPedido == null) totalPedido = BigDecimal.ZERO;
        if (pagado == null) pagado = false;
        if (estadoPedido == null) estadoPedido = "pendiente";
        if (tipoPedido == null) tipoPedido = "local";
    }
}

package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "Notificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idNotificacion;

    @Column(nullable = false)
    private Integer idUsuario;

    @Column
    private Integer idPedido;

    @Column
    private Integer idVenta;

    @Column(nullable = false, length = 20)
    private String tipoNotificacion;

    @Column(nullable = false, length = 120)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(nullable = false)
    private Boolean leido;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaNotificacion;

    @PrePersist
    protected void onCreate() {
        fechaNotificacion = LocalDateTime.now();
        if (leido == null) leido = false;
        if (tipoNotificacion == null) tipoNotificacion = "sistema";
    }
}

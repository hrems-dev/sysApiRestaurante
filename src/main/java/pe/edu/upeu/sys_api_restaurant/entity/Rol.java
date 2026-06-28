package pe.edu.upeu.sys_api_restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "Rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Rol implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer idRol;

    @Column(nullable = false, length = 50, unique = true)
    private String nombreRol;

    @Column(length = 150)
    private String descripcionRol;

    @Column(nullable = false)
    private Boolean estadoRol;

    @Override
    public String getAuthority() {
        return "ROLE_" + nombreRol;
    }
}

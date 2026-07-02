package pe.edu.upeu.sys_api_restaurant.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/roles/**").permitAll()
                .requestMatchers("/api/roles/**").hasRole("ADMIN")
                .requestMatchers("/api/usuarios/**").hasRole("ADMIN")
                .requestMatchers("/api/onboarding/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/metodos-pago/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/metodos-pago/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/metodos-pago/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/metodos-pago/**").hasRole("ADMIN")
                .requestMatchers("/api/configuracion/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/lugares/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/lugares/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/lugares/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/lugares/**").hasRole("ADMIN")
                .requestMatchers("/api/qr/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categorias-producto/**").permitAll()
                .requestMatchers("/api/categorias-producto/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/productos/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/productos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/pedidos/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/pedidos/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/pedidos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/pedidos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/pagos/**").authenticated()
                .requestMatchers(HttpMethod.POST, "/api/pagos/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/api/pagos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/pagos/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/reservas/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/reservas/**").authenticated()
                .requestMatchers("/api/reservas/**").hasRole("ADMIN")
                .requestMatchers("/api/ventas/**").hasRole("ADMIN")
                .requestMatchers("/api/documentos-venta/**").hasRole("ADMIN")
                .requestMatchers("/api/notificaciones/**").authenticated()
                .requestMatchers("/api/cocina/**").hasRole("ADMIN")
                .requestMatchers("/api/contabilidad/**").hasRole("ADMIN")
                .requestMatchers("/api/delivery/**").hasRole("ADMIN")
                .requestMatchers("/api/facturacion/**").hasRole("ADMIN")
                .requestMatchers("/api/reportes/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("*"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}

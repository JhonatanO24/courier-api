package com.courier.shared.events.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Deshabilitamos CSRF para poder usar Postman/Swagger sin tokens extra
                .authorizeHttpRequests(auth -> auth
                        // Lista extendida de rutas para que Swagger cargue correctamente
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/api/docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Cualquier otra petición (como tus controladores) también requiere login
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()); // Activa el popup de login en el navegador

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // Usuario para pruebas
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("admin123")) // La clave se guarda hasheada con BCrypt
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(admin);
    }
}
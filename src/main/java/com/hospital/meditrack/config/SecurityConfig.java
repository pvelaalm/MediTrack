package com.hospital.meditrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // TURNOS
                .requestMatchers(HttpMethod.GET, "/api/turnos/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/turnos/**").hasRole("SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/turnos/**").hasRole("SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/turnos/**").hasRole("SUPERVISOR")

                // PACIENTES
                .requestMatchers(HttpMethod.GET, "/api/pacientes/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/pacientes/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/pacientes/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**").hasRole("SUPERVISOR")

                // USUARIOS - Solo SUPERVISOR
                .requestMatchers("/api/usuarios/**").hasRole("SUPERVISOR")

                // TAREAS
                .requestMatchers(HttpMethod.GET, "/api/tareas/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/tareas/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/tareas/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/tareas/**").hasRole("SUPERVISOR")

                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());

        return http.build();
    }
}

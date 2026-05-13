package com.hospital.meditrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF: deshabilitado solo para API REST, habilitado para formularios web
            .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))

            .authorizeHttpRequests(auth -> auth
                // Recursos públicos
                .requestMatchers("/login", "/error", "/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()

                // VISTAS WEB por rol
                .requestMatchers("/enfermeria/**").hasRole("ENFERMERIA")
                .requestMatchers("/medicina/**").hasRole("MEDICINA")
                .requestMatchers("/supervisor/**").hasRole("SUPERVISOR")

                // API REST - TURNOS
                .requestMatchers(HttpMethod.GET, "/api/turnos/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/turnos/**").hasRole("SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/turnos/**").hasRole("SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/turnos/**").hasRole("SUPERVISOR")

                // API REST - PACIENTES
                .requestMatchers(HttpMethod.GET, "/api/pacientes/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/pacientes/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/pacientes/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/pacientes/**").hasRole("SUPERVISOR")

                // API REST - USUARIOS (solo SUPERVISOR)
                .requestMatchers("/api/usuarios/**").hasRole("SUPERVISOR")

                // API REST - TAREAS
                .requestMatchers(HttpMethod.GET, "/api/tareas/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.POST, "/api/tareas/**").hasAnyRole("MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.PUT, "/api/tareas/**").hasAnyRole("ENFERMERIA", "MEDICINA", "SUPERVISOR")
                .requestMatchers(HttpMethod.DELETE, "/api/tareas/**").hasRole("SUPERVISOR")

                // API REST - DASHBOARD TIEMPO REAL (SSE)
                .requestMatchers("/api/supervisor/dashboard/stream").hasRole("SUPERVISOR")

                .anyRequest().authenticated()
            )

            // Formulario de login personalizado
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            // Logout
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            // Para rutas /api/**: devolver 401 en lugar de redirigir al login
            .exceptionHandling(ex -> ex
                .defaultAuthenticationEntryPointFor(
                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                    PathPatternRequestMatcher.pathPattern("/api/**")
                )
            )

            // HTTP Basic para compatibilidad con clientes REST
            .httpBasic(withDefaults());

        return http.build();
    }
}

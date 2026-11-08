package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(CustomAuthenticationSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Configuración de CSRF (ignorar consola H2 para permitir consola web en dev)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
            )
            // Permitir frames para la consola web de H2
            .headers(headers -> headers
                .frameOptions(frame -> frame.sameOrigin())
            )
            // Middleware de Control de Acceso basado en Roles y Permisos (RBAC)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/login/oauth2/**", "/access-denied", "/h2-console/**", "/css/**", "/js/**", "/images/**").permitAll()
                // Catálogo, Blog público, Páginas dinámicas y Asistente de instalación
                .requestMatchers("/lms", "/lms/curso/**", "/blog/**", "/paginas/**", "/instalador/**").permitAll()
                // Creador de cursos, gestor de medios e instructor LMS
                .requestMatchers("/lms/instructor/**", "/lms/medios/**").hasAnyRole("PROFESOR", "ADMIN")
                // Configuración de Marca y Academia
                .requestMatchers("/lms/configuracion/**").hasRole("ADMIN")
                // Módulos administrativos tradicionales
                .requestMatchers("/admin/**", "/matriculas/**", "/finanzas/**").hasRole("ADMIN")
                // Módulo de PROFESORES
                .requestMatchers("/profesor/**").hasAnyRole("PROFESOR", "ADMIN")
                // Módulo de ESTUDIANTES
                .requestMatchers("/estudiante/**").hasAnyRole("ESTUDIANTE", "ADMIN")
                // Cualquier otra solicitud (incluyendo aula virtual /lms/aprender/** y compras) requiere estar autenticado
                .anyRequest().authenticated()
            )
            // Autenticación por formulario
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(successHandler)
                .failureUrl("/login?error=true")
                .permitAll()
            )
            // Cierre de sesión
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            // Manejador de acceso denegado (403 Forbidden)
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            );

        return http.build();
    }
}

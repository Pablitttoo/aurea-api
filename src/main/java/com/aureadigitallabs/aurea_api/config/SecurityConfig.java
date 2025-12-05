package com.aureadigitallabs.aurea_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // --- CORRECCIÓN AQUÍ ---
                        // Permitimos la ruta EXACTA de login y también registro de usuarios
                        .requestMatchers("/api/login").permitAll() // Login (AuthController)
                        .requestMatchers("/api/users/register").permitAll() // Registro (UserController)
                        .requestMatchers("/api/users/login").permitAll() // Login alternativo (UserController, por si
                                                                         // acaso)

                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/categories/**").permitAll()
                        .anyRequest().authenticated());

        return http.build();
    }
}
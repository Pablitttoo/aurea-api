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

                        // Login y Registro
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/users/**").permitAll()

                        // Productos y Categorías
                        .requestMatchers("/api/products/**").permitAll()
                        .requestMatchers("/api/categories/**").permitAll()

                        // --- AGREGA ESTO AQUÍ ---
                        // Permitimos crear y ver pedidos sin validar el token "falso"
                        .requestMatchers("/api/orders/**").permitAll()

                        .anyRequest().authenticated());

        return http.build();
    }
}
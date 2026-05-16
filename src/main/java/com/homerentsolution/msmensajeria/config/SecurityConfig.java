package com.homerentsolution.msmensajeria.config;

import com.homerentsolution.msmensajeria.security.JwtFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(
            JwtFilter jwtFilter) {

        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                .authorizeHttpRequests(auth -> auth

                        // login libre
                        .requestMatchers(
                                "/auth/**"
                        ).permitAll()

                        // GET mensajes → USER y ADMIN
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/mensajes/**"
                        ).hasAnyRole(
                                "USER",
                                "ADMIN"
                        )

                        // POST mensajes → ADMIN
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/mensajes/**"
                        ).hasRole(
                                "ADMIN"
                        )

                        // DELETE mensajes → ADMIN
                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/v1/mensajes/**"
                        ).hasRole(
                                "ADMIN"
                        )

                        // resto autenticado
                        .anyRequest().authenticated()
                )

                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }
}

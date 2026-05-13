package com.homerentsolution.msmensajeria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        // permitir swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**"
                        ).permitAll()

                        // permitir login
                        .requestMatchers(
                                "/auth/**"
                        ).permitAll()

                        // todo lo demás protegido
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form.disable());

        return http.build();
    }
}
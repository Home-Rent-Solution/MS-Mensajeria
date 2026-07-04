package com.homerentsolution.msmensajeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Render standalone:
// Se comenta Feign para desplegar este MS de forma independiente en Render.
// Reactivar cuando vuelvan a levantarse todos los microservicios juntos.
// import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
// @EnableFeignClients
public class MsMensajeriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsMensajeriaApplication.class, args);
    }

}
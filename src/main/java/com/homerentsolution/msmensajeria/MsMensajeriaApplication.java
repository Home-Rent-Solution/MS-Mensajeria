package com.homerentsolution.msmensajeria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsMensajeriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsMensajeriaApplication.class, args);
    }

}

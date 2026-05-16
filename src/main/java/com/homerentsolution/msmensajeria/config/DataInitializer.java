package com.homerentsolution.msmensajeria.config;

import com.homerentsolution.msmensajeria.model.Mensajeria;

import com.homerentsolution.msmensajeria.repository.MensajeriaRepository;

import org.springframework.boot.CommandLineRunner;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    private static final Logger log =
            LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(
            MensajeriaRepository repository) {

        return args -> {

            // evitar duplicados
            if (repository.count() == 0) {

                Mensajeria mensaje1 =
                        new Mensajeria();

                mensaje1.setContenido(
                        "Hola, bienvenida a Home Rent"
                );

                mensaje1.setIdEmisor(1L);

                mensaje1.setIdReceptor(2L);

                mensaje1.setFecha(
                        LocalDateTime.now()
                );

                repository.save(mensaje1);

                Mensajeria mensaje2 =
                        new Mensajeria();

                mensaje2.setContenido(
                        "Tu reserva fue confirmada"
                );

                mensaje2.setIdEmisor(2L);

                mensaje2.setIdReceptor(1L);

                mensaje2.setFecha(
                        LocalDateTime.now()
                );

                repository.save(mensaje2);

                log.info("Datos iniciales de mensajeria cargados");
            }
        };
    }
}

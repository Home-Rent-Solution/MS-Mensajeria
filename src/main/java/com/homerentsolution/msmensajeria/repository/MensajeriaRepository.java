package com.homerentsolution.msmensajeria.repository;

import com.homerentsolution.msmensajeria.model.Mensajeria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensajeriaRepository extends JpaRepository<Mensajeria, Long> {

    // Mensajes enviados por un emisor
    List<Mensajeria> findByIdEmisorOrderByFechaDesc(
            Long idEmisor
    );

    // Mensajes recibidos por un receptor
    List<Mensajeria> findByIdReceptorOrderByFechaDesc(
            Long idReceptor
    );

    // Conversación completa entre dos usuarios
    @Query("""
            SELECT m
            FROM Mensajeria m
            WHERE
                (m.idEmisor = :emisor AND m.idReceptor = :receptor)
                OR
                (m.idEmisor = :receptor AND m.idReceptor = :emisor)
            ORDER BY m.fecha ASC
            """)
    List<Mensajeria> obtenerConversacion(
            @Param("emisor") Long emisor,
            @Param("receptor") Long receptor
    );
}

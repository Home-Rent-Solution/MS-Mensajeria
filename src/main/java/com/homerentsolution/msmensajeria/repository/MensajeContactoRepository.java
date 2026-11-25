package com.homerentsolution.msmensajeria.repository;

import com.homerentsolution.msmensajeria.model.MensajeContacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeContactoRepository extends JpaRepository<MensajeContacto, Long> {

    // los más recientes primero, para el panel del administrador
    List<MensajeContacto> findAllByOrderByFechaDesc();
}

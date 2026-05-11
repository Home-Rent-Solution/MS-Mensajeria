package com.homerentsolution.msmensajeria.service;

import com.homerentsolution.msmensajeria.model.Mensajeria;
import com.homerentsolution.msmensajeria.repository.MensajeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeriaService {

    @Autowired
    private MensajeriaRepository repository;

    public List<Mensajeria> listar() {
        return repository.findAll();
    }

    public Mensajeria guardar(Mensajeria mensaje) {
        mensaje.setFecha(LocalDateTime.now());
        return repository.save(mensaje);
    }
}
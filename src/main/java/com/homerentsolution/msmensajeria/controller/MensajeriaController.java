package com.homerentsolution.msmensajeria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.homerentsolution.msmensajeria.service.MensajeriaService;
import com.homerentsolution.msmensajeria.model.Mensajeria;

@RestController
@RequestMapping("/api/v1/mensajes")
public class MensajeriaController {
    @Autowired
    private MensajeriaService service;

    @GetMapping
    public List<Mensajeria> listar() {
        return service.listar();
    }

    @PostMapping
    public Mensajeria guardar(@RequestBody Mensajeria mensaje) {
        return service.guardar(mensaje);
    }
}

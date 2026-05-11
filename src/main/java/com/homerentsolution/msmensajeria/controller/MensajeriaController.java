package com.homerentsolution.msmensajeria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.homerentsolution.msmensajeria.service.MensajeriaService;
import com.homerentsolution.msmensajeria.model.Mensajeria;

@RestController
@RequestMapping("/api/v1/mensajes")
public class MensajeriaController {

    //Inyectar automaticamente las dependencias
    @Autowired
    private MensajeriaService service;

    //metodo Listar
    @GetMapping
    public List<Mensajeria> listar() {
        return service.listar();
    }

    //guardar en body
    @PostMapping
    public Mensajeria guardar(@RequestBody Mensajeria mensaje) {
        return service.guardar(mensaje);
    }

    //buscar por Id
    @GetMapping("/{id}")
    public Mensajeria buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    //Put para actualizar
    @PutMapping("/{id}")
    public Mensajeria actualizar(@PathVariable Long id, @RequestBody Mensajeria mensaje) {
        return service.actualizar(id, mensaje);
    }

    //eliminar
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}

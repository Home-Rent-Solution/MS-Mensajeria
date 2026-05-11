package com.homerentsolution.msmensajeria.controller;

import java.util.List;

import com.homerentsolution.msmensajeria.dto.MensajeriaRequestDTO;
import com.homerentsolution.msmensajeria.dto.MensajeriaResponseDTO;
import jakarta.validation.Valid;
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

    //guardar mensajeriaRequestDto queda desacoplado de la entidad
    @PostMapping
    public MensajeriaResponseDTO guardar(
            @Valid @RequestBody MensajeriaRequestDTO dto) {

        return service.guardar(dto);
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

    // Buscar mensajes por emisor
    @GetMapping("/emisor/{id}")
    public List<Mensajeria> buscarPorEmisor(@PathVariable Long id) {
        return service.buscarPorEmisor(id);
    }

    // Buscar mensajes por receptor
    @GetMapping("/receptor/{id}")
    public List<Mensajeria> buscarPorReceptor(@PathVariable Long id) {

        return service.buscarPorReceptor(id);
    }

    // Buscar conversación entre emisor y receptor
    @GetMapping("/conversacion")
    public List<Mensajeria> buscarConversacion(
            @RequestParam Long emisor,
            @RequestParam Long receptor) {

        return service.buscarConversacion(emisor, receptor);
    }
}

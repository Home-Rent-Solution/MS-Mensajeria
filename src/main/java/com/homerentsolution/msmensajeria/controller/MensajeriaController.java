package com.homerentsolution.msmensajeria.controller;

import java.util.List;

import com.homerentsolution.msmensajeria.dto.MensajeriaRequestDTO;
import com.homerentsolution.msmensajeria.dto.MensajeriaResponseDTO;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.homerentsolution.msmensajeria.service.MensajeriaService;
import com.homerentsolution.msmensajeria.model.Mensajeria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/mensajes")
public class MensajeriaController {

    //Inyectar automaticamente las dependencias
    @Autowired
    private MensajeriaService service;

    // Logger para registrar eventos del controller
    private static final Logger log =
            LoggerFactory.getLogger(MensajeriaController.class);

    // Listar con ResponseEntity reponde 200 ok
    @GetMapping
    public ResponseEntity<List<Mensajeria>> listar() {
        log.info("Listando todos los mensajes");
        return ResponseEntity.ok(
                service.listar());
    }

    //guardar RequestDto responda responseentity- devuelve 201 created
    @PostMapping
    public ResponseEntity<MensajeriaResponseDTO> guardar(
            @Valid @RequestBody MensajeriaRequestDTO dto) {

        log.info(
                "Guardando mensaje desde emisor {} hacia receptor {}",
                dto.getIdEmisor(),
                dto.getIdReceptor()
        );

        MensajeriaResponseDTO respuesta =
                service.guardar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }

    //buscar por Id, en responseEntity responde 200 ok
    @GetMapping("/{id}")
    public ResponseEntity<Mensajeria> buscar(
            @PathVariable Long id) {

        log.info("Buscando mensaje con ID: {}", id);

        return ResponseEntity.ok(
                service.buscarPorId(id));
    }

    //Put para actualizar, devuelve 200 ok
    @PutMapping("/{id}")
    public ResponseEntity<Mensajeria> actualizar(
            @PathVariable Long id,
            @RequestBody Mensajeria mensaje) {

        log.info("Actualizando mensaje con ID: {}", id);

        Mensajeria actualizado = service.actualizar(id, mensaje);

        return ResponseEntity.ok(actualizado);
    }

    //eliminar y devuelve 204  no content
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.warn("Eliminando mensaje con ID: {}", id);

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }

    // Buscar mensajes por emisor
    @GetMapping("/emisor/{id}")
    public ResponseEntity<List<Mensajeria>> buscarPorEmisor(
            @PathVariable Long id) {

        log.info("Buscando mensajes del emisor: {}", id);

        return ResponseEntity.ok(
                service.buscarPorEmisor(id));
    }

    // Buscar mensajes por receptor
    @GetMapping("/receptor/{id}")
    public ResponseEntity<List<Mensajeria>> buscarPorReceptor(
            @PathVariable Long id) {

        log.info("Buscando mensajes del receptor: {}", id);

        return ResponseEntity.ok(
                service.buscarPorReceptor(id));
    }

    // Buscar conversación entre emisor y receptor
    @GetMapping("/conversacion")
    public ResponseEntity<List<Mensajeria>> buscarConversacion(
            @RequestParam Long emisor,
            @RequestParam Long receptor) {

        log.info(
                "Buscando conversación entre emisor {} y receptor {}",
                emisor,
                receptor
        );

        return ResponseEntity.ok(
                service.buscarConversacion(emisor, receptor));
    }
}

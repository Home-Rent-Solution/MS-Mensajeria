package com.homerentsolution.msmensajeria.controller;

import com.homerentsolution.msmensajeria.dto.ContactoRequestDTO;
import com.homerentsolution.msmensajeria.dto.ContactoResponseDTO;
import com.homerentsolution.msmensajeria.service.ContactoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// Formulario de contacto del sitio. Enviar es público; ver y eliminar es solo para el
// administrador (lo controla el API Gateway).
@RestController
@RequestMapping("/api/v1/contacto")
@Tag(name = "Contacto", description = "Mensajes del formulario de contacto del sitio")
public class ContactoController {

    private final ContactoService service;

    @Autowired
    public ContactoController(ContactoService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Enviar un mensaje de contacto", description = "Lo usa el formulario de contacto. No requiere iniciar sesión.")
    @ApiResponse(responseCode = "201", description = "Mensaje recibido")
    @ApiResponse(responseCode = "400", description = "Datos inválidos")
    public ResponseEntity<ContactoResponseDTO> enviar(@Valid @RequestBody ContactoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(dto));
    }

    @GetMapping
    @Operation(summary = "Listar mensajes de contacto", description = "Para el panel del administrador, los más recientes primero.")
    @ApiResponse(responseCode = "200", description = "Mensajes de contacto")
    public ResponseEntity<List<ContactoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un mensaje de contacto")
    @ApiResponse(responseCode = "204", description = "Eliminado")
    @ApiResponse(responseCode = "400", description = "El mensaje no existe")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

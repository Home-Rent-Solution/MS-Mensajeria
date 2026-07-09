package com.homerentsolution.msmensajeria.controller;

import java.util.List;

import com.homerentsolution.msmensajeria.dto.MensajeriaRequestDTO;
import com.homerentsolution.msmensajeria.dto.MensajeriaResponseDTO;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.homerentsolution.msmensajeria.service.MensajeriaService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/mensajes")

//Agrupa todos los endpoints de mensajes en Swagger
@Tag(
        name = "Mensajería",
        description = "Operaciones relacionadas con mensajes entre usuarios"
)
public class MensajeriaController {

    //Inyeccion de dependencias mediante constructor
    private final MensajeriaService service;

    @Autowired
    public MensajeriaController(MensajeriaService service) {
        this.service = service;
    }

    // Logger para registrar eventos del controller
    private static final Logger log =
            LoggerFactory.getLogger(MensajeriaController.class);


    // LISTAR MENSAJES
    @Operation(
            summary = "Listar mensajes",
            description = "Obtiene todos los mensajes registrados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensajes obtenidos correctamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    // Devuelve 200 OK con la lista de mensajes
    @GetMapping
    public ResponseEntity<List<MensajeriaResponseDTO>> listar() {
        log.info("Listando todos los mensajes");
        return ResponseEntity.ok(service.listar());
    }


    //CREAR MENSAJE

    @Operation(
            summary = "Crear mensaje",
            description = "Registra un nuevo mensaje entre usuarios"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mensaje creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    //  Usa RequestDTO y devuelve ResponseDTO
    //  Retorna 201 CREATED
    @PostMapping
    public ResponseEntity<MensajeriaResponseDTO> guardar(
            @Valid @RequestBody MensajeriaRequestDTO dto) {

        log.info(
                "Guardando mensaje desde emisor {} hacia receptor {}",
                dto.getIdEmisor(),
                dto.getIdReceptor()
        );

        MensajeriaResponseDTO respuesta = service.guardar(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(respuesta);
    }


    // BUSCAR POR ID
    @Operation(
            summary = "Buscar mensaje",
            description = "Obtiene un mensaje mediante su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensaje encontrado"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    //  Devuelve 200 OK si existe
    @GetMapping("/{id}")
    public ResponseEntity<MensajeriaResponseDTO> buscar(
            @PathVariable Long id) {

        log.info("Buscando mensaje con ID: {}", id);

        return ResponseEntity.ok(service.buscarPorId(id));
    }

    // ACTUALIZAR MENSAJE
    @Operation(
            summary = "Actualizar mensaje",
            description = "Actualiza un mensaje existente"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensaje actualizado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    //  Recibe DTO
    // La lógica se maneja en el Service
    // Devuelve 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<MensajeriaResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody MensajeriaRequestDTO dto) {

        log.info("Actualizando mensaje con ID: {}", id);

        MensajeriaResponseDTO actualizado =
                service.actualizar(id, dto);

        return ResponseEntity.ok(actualizado);
    }

    // ELIMINAR MENSAJE

    @Operation(
            summary = "Eliminar mensaje",
            description = "Elimina un mensaje por su identificador"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Mensaje eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Mensaje no encontrado")
    })
    // ✔ No retorna contenido → 204 NO CONTENT
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Long id) {

        log.warn("Eliminando mensaje con ID: {}", id);

        service.eliminar(id);

        return ResponseEntity.noContent().build();
    }


    // BUSCAR POR EMISOR

    @Operation(
            summary = "Buscar por emisor",
            description = "Obtiene todos los mensajes enviados por un usuario"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensajes encontrados"),
            @ApiResponse(responseCode = "404", description = "No existen mensajes")
    })
    @GetMapping("/emisor/{id}")
    public ResponseEntity<List<MensajeriaResponseDTO>> buscarPorEmisor(
            @PathVariable Long id) {

        log.info("Buscando mensajes del emisor: {}", id);

        return ResponseEntity.ok(
                service.buscarPorEmisor(id));
    }


    // BUSCAR POR RECEPTOR

    @Operation(
            summary = "Buscar por receptor",
            description = "Obtiene todos los mensajes recibidos por un usuario"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mensajes encontrados"),
            @ApiResponse(responseCode = "404", description = "No existen mensajes")
    })
    @GetMapping("/receptor/{id}")
    public ResponseEntity<List<MensajeriaResponseDTO>> buscarPorReceptor(
            @PathVariable Long id) {

        log.info("Buscando mensajes del receptor: {}", id);

        return ResponseEntity.ok(
                service.buscarPorReceptor(id));
    }


    //  BUSCAR CONVERSACIÓN

    @Operation(
            summary = "Buscar conversación",
            description = "Obtiene los mensajes intercambiados entre emisor y receptor"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Conversación encontrada"),
            @ApiResponse(responseCode = "404", description = "No existe conversación")
    })
    // ✔ Usa RequestParam → ejemplo de query params
    @GetMapping("/conversacion")
    public ResponseEntity<List<MensajeriaResponseDTO>> buscarConversacion(
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
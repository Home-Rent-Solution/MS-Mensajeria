package com.homerentsolution.msmensajeria.dto;

import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(
        name = "MensajeriaResponseDTO",
        description = "Datos que se devuelven de un mensaje"
)
public class MensajeriaResponseDTO {
    @Schema
            (description = "ID del mensaje", example = "10")
    private Long idMensaje;

    @Schema
            (description = "Contenido del mensaje", example = "Hola, ¿cómo estás?")
    private String contenido;

    @Schema
            (description = "ID del emisor", example = "1")
    private Long idEmisor;

    @Schema
            (description = "ID del receptor", example = "2")
    private Long idReceptor;

    @Schema
            (description = "Fecha del mensaje", example = "2025-06-10T10:30:00")
    private LocalDateTime fecha;

}

package com.homerentsolution.msmensajeria.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MensajeriaResponseDTO {

    private Long idMensaje;
    private String contenido;
    private Long idEmisor;
    private Long idReceptor;
    private LocalDateTime fecha;
}

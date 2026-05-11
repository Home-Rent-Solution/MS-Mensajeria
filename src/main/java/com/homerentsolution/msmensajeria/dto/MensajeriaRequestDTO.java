package com.homerentsolution.msmensajeria.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MensajeriaRequestDTO {
    private String contenido;
    private Long idEmisor;
    private Long idReceptor;
}

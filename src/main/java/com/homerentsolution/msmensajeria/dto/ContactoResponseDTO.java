package com.homerentsolution.msmensajeria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContactoResponseDTO {

    private Long idContacto;
    private String nombre;
    private String correo;
    private String telefono;
    private String motivo;
    private String mensaje;
    private LocalDateTime fecha;
}

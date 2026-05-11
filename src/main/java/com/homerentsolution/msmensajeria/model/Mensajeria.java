package com.homerentsolution.msmensajeria.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

public class Mensajeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    private LocalDateTime fecha;

    //Validaciones
    //no estar vacio y tener el largo de min 3 y max 100
    @NotBlank(message = "El contenido no puede estar vacío")
    @Size(min = 3, max = 100,
            message = "El mensaje debe tener entre 3 y 100 caracteres")
    private String contenido;

    //valor no puede ser nulo y debe ser numero positivo
    @NotNull(message = "El id del emisor es obligatorio")
    @Positive(message = "El id del emisor debe ser positivo")
    private Long idEmisor;

    //valor no puede ser nulo y debe ser numero positivo
    @NotNull(message = "El id del receptor es obligatorio")
    @Positive(message = "El id del receptor debe ser positivo")
    private Long idReceptor;


}
package com.homerentsolution.msmensajeria.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Getter
@Setter
public class MensajeriaRequestDTO {

    //Validaciones contenido
    @NotBlank(
            message = "El contenido no puede estar vacío"
    )

    @Size(
            min = 3,
            max = 100,
            message =
                    "El mensaje debe tener entre 3 y 100 caracteres"
    )
    private String contenido;

    //validacion id emisor
    @NotNull(
            message = "El ID del emisor es obligatorio"
    )

    @Positive(
            message = "El ID del emisor debe ser positivo"
    )
    private Long idEmisor;

    //validacion id receptor
    @NotNull(
            message = "El ID del receptor es obligatorio"
    )

    @Positive(
            message = "El ID del receptor debe ser positivo"
    )
    private Long idReceptor;
}

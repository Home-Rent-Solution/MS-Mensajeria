package com.homerentsolution.msmensajeria.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(
        name = "MensajeriaRequestDTO",
        description = "Datos necesarios para crear un mensaje"
)
public class MensajeriaRequestDTO {

    //Validaciones contenido
    // @Schema Define la documentación del DTO en Swagger
    @Schema(
            description = "Contenido del mensaje",
            example = "Hola, ¿cómo estás?"
    )
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
    @Schema(
            description = "ID del usuario emisor",
            example = "1"
    )
    @NotNull(
            message = "El ID del emisor es obligatorio"
    )

    @Positive(
            message = "El ID del emisor debe ser positivo"
    )
    private Long idEmisor;

    //validacion id receptor
    @Schema(
            description = "ID del usuario receptor",
            example = "2"
    )
    @NotNull(
            message = "El ID del receptor es obligatorio"
    )

    @Positive(
            message = "El ID del receptor debe ser positivo"
    )
    private Long idReceptor;
}

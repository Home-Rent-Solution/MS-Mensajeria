package com.homerentsolution.msmensajeria.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

// Datos del formulario de contacto, con las mismas reglas que valida el frontend
@Getter
@Setter
public class ContactoRequestDTO {

    @Schema(example = "María Soto")
    @NotBlank(message = "Ingresa tu nombre")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚÜÑáéíóúüñ' -]+$", message = "El nombre solo puede tener letras y espacios")
    private String nombre;

    @Schema(example = "maria.soto@gmail.com")
    @NotBlank(message = "Ingresa tu correo electrónico")
    @Email(message = "Ingresa un correo válido")
    @Size(max = 100, message = "El correo puede tener máximo 100 caracteres")
    private String correo;

    // opcional: de 8 a 11 dígitos, se aceptan espacios, +, guiones y paréntesis
    @Schema(example = "+56 9 1234 5678")
    @Pattern(regexp = "^$|^[\\s()+-]*(\\d[\\s()+-]*){8,11}$",
            message = "Ingresa un teléfono de 8 a 11 dígitos")
    private String telefono;

    @Schema(example = "consulta")
    @NotBlank(message = "Selecciona el motivo de tu mensaje")
    @Pattern(regexp = "consulta|publicar|reserva|sugerencia|otro", message = "El motivo no es válido")
    private String motivo;

    @Schema(example = "Hola, quisiera saber si la cabaña de Pucón tiene estacionamiento.")
    @NotBlank(message = "Escribe tu mensaje")
    @Size(min = 10, max = 500, message = "El mensaje debe tener entre 10 y 500 caracteres")
    private String mensaje;
}

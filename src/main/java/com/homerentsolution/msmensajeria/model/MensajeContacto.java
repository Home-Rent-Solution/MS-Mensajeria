package com.homerentsolution.msmensajeria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

// Mensaje que un visitante envía desde el formulario de contacto del sitio.
// Es distinto de Mensajeria, que guarda mensajes entre usuarios (emisor y receptor).
@Entity
@Table(name = "mensajes_contacto")
@Getter
@Setter
@NoArgsConstructor
public class MensajeContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContacto;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String correo;

    @Column(length = 20)
    private String telefono;

    // consulta, publicar, reserva, sugerencia u otro (los mismos valores del frontend)
    @Column(nullable = false, length = 20)
    private String motivo;

    @Column(nullable = false, length = 500)
    private String mensaje;

    @Column(nullable = false)
    private LocalDateTime fecha;
}

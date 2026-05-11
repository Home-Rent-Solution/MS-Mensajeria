package com.homerentsolution.msmensajeria.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Mensajeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    private String contenido;
    private LocalDateTime fecha;
    private Long idEmisor;
    @Getter
    @Setter
    private Long idReceptor;

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Long getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Long idEmisor) {
        this.idEmisor = idEmisor;
    }

}
package model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Mensajeria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    private String contenido;
    private LocalDateTime fecha;
    private Long idEmisor;
    private Long idReceptor;

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
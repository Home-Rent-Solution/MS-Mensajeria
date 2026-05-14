package com.homerentsolution.msmensajeria.service;

import com.homerentsolution.msmensajeria.dto.MensajeriaRequestDTO;
import com.homerentsolution.msmensajeria.dto.MensajeriaResponseDTO;
import com.homerentsolution.msmensajeria.model.Mensajeria;
import com.homerentsolution.msmensajeria.repository.MensajeriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homerentsolution.msmensajeria.client.InquilinoClient;
import com.homerentsolution.msmensajeria.client.ReservaClient;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeriaService {

    @Autowired
    private MensajeriaRepository repository;

    //se agrega Feign para comunicación entre servicios
    @Autowired
    private InquilinoClient inquilinoClient;

    // Feign para comunicación con reservas
    @Autowired
    private ReservaClient reservaClient;

    // el CRUD completo
    //listartodo mensajeria
    public List<Mensajeria> listar() {

        return repository.findAll();
    }

    //guardar en mesjaeriaResponseDto
    public MensajeriaResponseDTO guardar(MensajeriaRequestDTO dto) {

        // Convertir DTO a Entity
        Mensajeria mensaje = new Mensajeria();

        mensaje.setContenido(dto.getContenido());
        mensaje.setIdEmisor(dto.getIdEmisor());
        mensaje.setIdReceptor(dto.getIdReceptor());

        // Regla de negocio
        if (mensaje.getIdEmisor().equals(mensaje.getIdReceptor())) {

            throw new RuntimeException(
                    "No puedes enviarte mensajes a ti mismo"
            );
        }

        // Validar existencia del emisor
        Boolean emisorExiste =
                inquilinoClient.validarInquilino(
                        dto.getIdEmisor()
                );

        if (!emisorExiste) {

            throw new RuntimeException(
                    "El emisor no existe"
            );
        }

        // Validar existencia del receptor
        Boolean receptorExiste =
                inquilinoClient.validarInquilino(
                        dto.getIdReceptor()
                );

        if (!receptorExiste) {

            throw new RuntimeException(
                    "El receptor no existe"
            );
        }

        // Validar existencia de reserva
        try {

            reservaClient.buscarReserva(1);

        } catch (Exception e) {

            throw new RuntimeException(
                    "No existe una reserva válida para permitir mensajería"
            );
        }

        // Fecha automática
        mensaje.setFecha(LocalDateTime.now());

        // Guardar en BD
        Mensajeria guardado = repository.save(mensaje);

        // Convertir Entity a ResponseDTO
        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(guardado.getIdMensaje());
        response.setContenido(guardado.getContenido());
        response.setIdEmisor(guardado.getIdEmisor());
        response.setIdReceptor(guardado.getIdReceptor());
        response.setFecha(guardado.getFecha());

        return response;
    }

    //buscar por Id
    public Mensajeria buscarPorId(Long id) {

        return repository.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Mensaje no encontrado"
                        ));
    }

    //actualizar
    public Mensajeria actualizar(
            Long id,
            Mensajeria mensajeActualizado) {

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Mensaje no encontrado"
                        ));

        mensaje.setContenido(
                mensajeActualizado.getContenido()
        );

        mensaje.setIdEmisor(
                mensajeActualizado.getIdEmisor()
        );

        mensaje.setIdReceptor(
                mensajeActualizado.getIdReceptor()
        );

        return repository.save(mensaje);
    }

    //eliminar
    public void eliminar(Long id) {

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Mensaje no encontrado"
                        ));

        repository.delete(mensaje);
    }

    // Buscar mensajes por emisor
    public List<Mensajeria> buscarPorEmisor(
            Long idEmisor) {

        List<Mensajeria> mensajes =
                repository.findByIdEmisorOrderByFechaDesc(
                        idEmisor
                );

        if (mensajes.isEmpty()) {

            throw new RuntimeException(
                    "No existen mensajes para este emisor"
            );
        }

        return mensajes;
    }

    // Buscar mensajes por receptor
    public List<Mensajeria> buscarPorReceptor(
            Long idReceptor) {

        List<Mensajeria> mensajes =
                repository.findByIdReceptor(idReceptor);

        if (mensajes.isEmpty()) {

            throw new RuntimeException(
                    "No existen mensajes para este receptor"
            );
        }

        return mensajes;
    }

    // Buscar conversación entre emisor y receptor
    public List<Mensajeria> buscarConversacion(
            Long idEmisor,
            Long idReceptor) {

        List<Mensajeria> mensajes =
                repository.findByIdEmisorAndIdReceptor(
                        idEmisor,
                        idReceptor
                );

        if (mensajes.isEmpty()) {

            throw new RuntimeException(
                    "No existe conversación entre los usuarios"
            );
        }

        return mensajes;
    }
}
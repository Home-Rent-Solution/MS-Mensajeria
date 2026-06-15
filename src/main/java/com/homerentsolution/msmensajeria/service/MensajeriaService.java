package com.homerentsolution.msmensajeria.service;

import com.homerentsolution.msmensajeria.dto.MensajeriaRequestDTO;
import com.homerentsolution.msmensajeria.dto.MensajeriaResponseDTO;
import com.homerentsolution.msmensajeria.model.Mensajeria;
import com.homerentsolution.msmensajeria.repository.MensajeriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homerentsolution.msmensajeria.client.InquilinoClient;
import com.homerentsolution.msmensajeria.client.ReservaClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeriaService {

    private static final Logger log =
            LoggerFactory.getLogger(MensajeriaService.class);

    @Autowired
    private MensajeriaRepository repository;

    // Comunicación con microservicio Inquilino
    @Autowired
    private InquilinoClient inquilinoClient;

    // Comunicación con microservicio Reserva
    @Autowired
    private ReservaClient reservaClient;

    // Listar todos los mensajes
    public List<Mensajeria> listar() {

        log.info("Consultando todos los mensajes");

        return repository.findAll();
    }

    // Guardar mensaje
    public MensajeriaResponseDTO guardar(MensajeriaRequestDTO dto) {

        log.info(
                "Intentando enviar mensaje desde {} hacia {}",
                dto.getIdEmisor(),
                dto.getIdReceptor()
        );

        Mensajeria mensaje = new Mensajeria();

        mensaje.setContenido(dto.getContenido());
        mensaje.setIdEmisor(dto.getIdEmisor());
        mensaje.setIdReceptor(dto.getIdReceptor());

        // Regla de negocio
        if (mensaje.getIdEmisor().equals(mensaje.getIdReceptor())) {

            log.warn("El usuario intenta enviarse un mensaje a sí mismo");

            throw new RuntimeException(
                    "No puedes enviarte mensajes a ti mismo"
            );
        }

        // Validar emisor
        Boolean emisorExiste =
                inquilinoClient.validarInquilino(
                        dto.getIdEmisor()
                );

        if (!emisorExiste) {

            log.warn(
                    "El emisor {} no existe",
                    dto.getIdEmisor()
            );

            throw new RuntimeException(
                    "El emisor no existe"
            );
        }

        // Validar receptor
        Boolean receptorExiste =
                inquilinoClient.validarInquilino(
                        dto.getIdReceptor()
                );

        if (!receptorExiste) {

            log.warn(
                    "El receptor {} no existe",
                    dto.getIdReceptor()
            );

            throw new RuntimeException(
                    "El receptor no existe"
            );
        }

        // Validar reserva
        try {

            reservaClient.buscarReserva(1);

        } catch (Exception e) {

            log.error(
                    "No existe una reserva válida para permitir mensajería"
            );

            throw new RuntimeException(
                    "No existe una reserva válida para permitir mensajería"
            );
        }

        mensaje.setFecha(LocalDateTime.now());

        Mensajeria guardado =
                repository.save(mensaje);

        log.info(
                "Mensaje guardado correctamente con ID {}",
                guardado.getIdMensaje()
        );

        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(
                guardado.getIdMensaje()
        );

        response.setContenido(
                guardado.getContenido()
        );

        response.setIdEmisor(
                guardado.getIdEmisor()
        );

        response.setIdReceptor(
                guardado.getIdReceptor()
        );

        response.setFecha(
                guardado.getFecha()
        );

        return response;
    }

    // Buscar por ID
    public Mensajeria buscarPorId(Long id) {

        log.info(
                "Buscando mensaje con ID {}",
                id
        );

        return repository.findById(id)
                .orElseThrow(() -> {

                    log.warn(
                            "Mensaje {} no encontrado",
                            id
                    );

                    return new RuntimeException(
                            "Mensaje no encontrado"
                    );
                });
    }

    // Actualizar mensaje
    public Mensajeria actualizar(
            Long id,
            Mensajeria mensajeActualizado) {

        log.info(
                "Actualizando mensaje {}",
                id
        );

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

        Mensajeria actualizado =
                repository.save(mensaje);

        log.info(
                "Mensaje {} actualizado correctamente",
                id
        );

        return actualizado;
    }

    // Eliminar mensaje
    public void eliminar(Long id) {

        log.warn(
                "Eliminando mensaje {}",
                id
        );

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() ->

                        new RuntimeException(
                                "Mensaje no encontrado"
                        ));

        repository.delete(mensaje);

        log.info(
                "Mensaje {} eliminado correctamente",
                id
        );
    }

    // Buscar mensajes por emisor
    public List<Mensajeria> buscarPorEmisor(
            Long idEmisor) {

        log.info(
                "Buscando mensajes del emisor {}",
                idEmisor
        );

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

        log.info(
                "Buscando mensajes del receptor {}",
                idReceptor
        );

        List<Mensajeria> mensajes =
                repository.findByIdReceptor(
                        idReceptor
                );

        if (mensajes.isEmpty()) {

            throw new RuntimeException(
                    "No existen mensajes para este receptor"
            );
        }

        return mensajes;
    }

    // Buscar conversación
    public List<Mensajeria> buscarConversacion(
            Long idEmisor,
            Long idReceptor) {

        log.info(
                "Buscando conversación entre {} y {}",
                idEmisor,
                idReceptor
        );

        List<Mensajeria> mensajes =
                repository.obtenerConversacion(
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
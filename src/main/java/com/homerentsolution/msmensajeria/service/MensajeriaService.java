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

    @Autowired
    private InquilinoClient inquilinoClient;

    @Autowired
    private ReservaClient reservaClient;

    // Listar todos
    public List<MensajeriaResponseDTO> listar() {
        log.info("Consultando todos los mensajes");

        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    // Guardar
    public MensajeriaResponseDTO guardar(MensajeriaRequestDTO dto) {

        log.info("Enviando mensaje de {} a {}",
                dto.getIdEmisor(), dto.getIdReceptor());

        if (dto.getIdEmisor().equals(dto.getIdReceptor())) {
            throw new RuntimeException("No puedes enviarte mensajes a ti mismo");
        }

        if (!inquilinoClient.validarInquilino(dto.getIdEmisor())) {
            throw new RuntimeException("El emisor no existe");
        }

        if (!inquilinoClient.validarInquilino(dto.getIdReceptor())) {
            throw new RuntimeException("El receptor no existe");
        }

        try {
            reservaClient.buscarReserva(1);
        } catch (Exception e) {
            throw new RuntimeException("No existe una reserva válida");
        }

        Mensajeria mensaje = new Mensajeria();
        mensaje.setContenido(dto.getContenido());
        mensaje.setIdEmisor(dto.getIdEmisor());
        mensaje.setIdReceptor(dto.getIdReceptor());
        mensaje.setFecha(LocalDateTime.now());

        Mensajeria guardado = repository.save(mensaje);

        return convertirDTO(guardado);
    }

    // Buscar por ID
    public MensajeriaResponseDTO buscarPorId(Long id) {

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));

        return convertirDTO(mensaje);
    }

    //actualizar
    public MensajeriaResponseDTO actualizar(
            Long id,
            MensajeriaRequestDTO dto) {

        log.info("Actualizando mensaje {}", id);

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mensaje no encontrado"));

        mensaje.setContenido(dto.getContenido());
        mensaje.setIdEmisor(dto.getIdEmisor());
        mensaje.setIdReceptor(dto.getIdReceptor());

        Mensajeria actualizado = repository.save(mensaje);

        return convertirDTO(actualizado);
    }

    // Eliminar
    public void eliminar(Long id) {

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mensaje no encontrado"));

        repository.delete(mensaje);
    }

    // Buscar por emisor
    public List<MensajeriaResponseDTO> buscarPorEmisor(Long idEmisor) {

        List<Mensajeria> mensajes =
                repository.findByIdEmisorOrderByFechaDesc(idEmisor);

        if (mensajes.isEmpty()) {
            throw new RuntimeException("No existen mensajes para este emisor");
        }

        return mensajes.stream()
                .map(this::convertirDTO)
                .toList();
    }

    // Buscar por receptor
    public List<MensajeriaResponseDTO> buscarPorReceptor(Long idReceptor) {

        List<Mensajeria> mensajes =
                repository.findByIdReceptorOrderByFechaDesc(idReceptor);

        if (mensajes.isEmpty()) {
            throw new RuntimeException("No existen mensajes para este receptor");
        }

        return mensajes.stream()
                .map(this::convertirDTO)
                .toList();
    }

    // Conversación
    public List<MensajeriaResponseDTO> buscarConversacion(
            Long idEmisor,
            Long idReceptor) {

        List<Mensajeria> mensajes =
                repository.obtenerConversacion(idEmisor, idReceptor);

        if (mensajes.isEmpty()) {
            throw new RuntimeException("No existe conversación");
        }

        return mensajes.stream()
                .map(this::convertirDTO)
                .toList();
    }

    // Mapper
    private MensajeriaResponseDTO convertirDTO(Mensajeria mensaje) {

        MensajeriaResponseDTO dto = new MensajeriaResponseDTO();

        dto.setIdMensaje(mensaje.getIdMensaje());
        dto.setContenido(mensaje.getContenido());
        dto.setIdEmisor(mensaje.getIdEmisor());
        dto.setIdReceptor(mensaje.getIdReceptor());
        dto.setFecha(mensaje.getFecha());

        return dto;
    }
}
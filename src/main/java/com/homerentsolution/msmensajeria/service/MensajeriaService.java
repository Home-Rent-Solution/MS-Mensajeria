package com.homerentsolution.msmensajeria.service;

import com.homerentsolution.msmensajeria.dto.MensajeriaRequestDTO;
import com.homerentsolution.msmensajeria.dto.MensajeriaResponseDTO;
import com.homerentsolution.msmensajeria.model.Mensajeria;
import com.homerentsolution.msmensajeria.repository.MensajeriaRepository;

/*
 * Render standalone:
 * Imports Feign originales comentados para no perder la información.
 * Reactivar cuando MS-Inquilinos y MS-Reservas estén desplegados/activos junto a Mensajeria.
 *
 * import com.homerentsolution.msmensajeria.client.InquilinoClient;
 * import com.homerentsolution.msmensajeria.client.ReservaClient;
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /*
     * Render standalone:
     * Clients Feign originales comentados. Si se dejan activos en Render con solo este MS,
     * las llamadas a servicios dormidos pueden generar demoras o fallos.
     *
     * @Autowired
     * private InquilinoClient inquilinoClient;
     *
     * @Autowired
     * private ReservaClient reservaClient;
     */

    public List<MensajeriaResponseDTO> listar() {
        log.info("Consultando todos los mensajes");

        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public MensajeriaResponseDTO guardar(MensajeriaRequestDTO dto) {

        log.info("Guardando mensaje de {} a {}",
                dto.getIdEmisor(), dto.getIdReceptor());

        if (dto.getIdEmisor().equals(dto.getIdReceptor())) {
            throw new RuntimeException("No puedes enviarte mensajes a ti mismo");
        }

        /*
         * Render standalone:
         * Validaciones remotas comentadas temporalmente. El CRUD queda local para Render.
         * Reactivar cuando MS-Inquilinos y MS-Reservas estén levantados y disponibles.
         *
         * if (!inquilinoClient.validarInquilino(dto.getIdEmisor())) {
         *     throw new RuntimeException("El emisor no existe");
         * }
         *
         * if (!inquilinoClient.validarInquilino(dto.getIdReceptor())) {
         *     throw new RuntimeException("El receptor no existe");
         * }
         *
         * try {
         *     reservaClient.buscarReserva(1);
         * } catch (Exception e) {
         *     throw new RuntimeException("No existe una reserva válida");
         * }
         */

        Mensajeria mensaje = new Mensajeria();
        mensaje.setContenido(dto.getContenido());
        mensaje.setIdEmisor(dto.getIdEmisor());
        mensaje.setIdReceptor(dto.getIdReceptor());
        mensaje.setFecha(LocalDateTime.now());

        Mensajeria guardado = repository.save(mensaje);

        return convertirDTO(guardado);
    }

    public MensajeriaResponseDTO buscarPorId(Long id) {

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mensaje no encontrado"));

        return convertirDTO(mensaje);
    }

    public MensajeriaResponseDTO actualizar(Long id, MensajeriaRequestDTO dto) {

        log.info("Actualizando mensaje {}", id);

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mensaje no encontrado"));

        if (dto.getIdEmisor().equals(dto.getIdReceptor())) {
            throw new RuntimeException("No puedes enviarte mensajes a ti mismo");
        }

        mensaje.setContenido(dto.getContenido());
        mensaje.setIdEmisor(dto.getIdEmisor());
        mensaje.setIdReceptor(dto.getIdReceptor());

        Mensajeria actualizado = repository.save(mensaje);

        return convertirDTO(actualizado);
    }

    public void eliminar(Long id) {

        Mensajeria mensaje = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Mensaje no encontrado"));

        repository.delete(mensaje);
    }

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
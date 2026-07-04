package com.homerentsolution.msmensajeria.service;

import com.homerentsolution.msmensajeria.client.InquilinoClient;
import com.homerentsolution.msmensajeria.client.ReservaClient;
import com.homerentsolution.msmensajeria.dto.MensajeriaRequestDTO;
import com.homerentsolution.msmensajeria.dto.MensajeriaResponseDTO;
import com.homerentsolution.msmensajeria.model.Mensajeria;
import com.homerentsolution.msmensajeria.repository.MensajeriaRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MensajeriaServiceTest {

    @Mock
    private MensajeriaRepository repository;

    @Mock
    private InquilinoClient inquilinoClient;

    @Mock
    private ReservaClient reservaClient;

    @InjectMocks
    private MensajeriaService service;

    @Test
    void listar_debeRetornarListaMensajes() {

        // GIVEN
        Mensajeria mensaje = new Mensajeria();
        mensaje.setIdMensaje(1L);
        mensaje.setContenido("Hola");
        mensaje.setIdEmisor(1L);
        mensaje.setIdReceptor(2L);
        mensaje.setFecha(LocalDateTime.now());

        when(repository.findAll())
                .thenReturn(List.of(mensaje));

        // WHEN
        List<MensajeriaResponseDTO> resultado =
                service.listar();

        // THEN
        assertEquals(1, resultado.size());
        assertEquals("Hola", resultado.get(0).getContenido());
        assertEquals(1L, resultado.get(0).getIdEmisor());
        assertEquals(2L, resultado.get(0).getIdReceptor());

        verify(repository, times(1)).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarMensaje() {

        // GIVEN
        Mensajeria mensaje = new Mensajeria();
        mensaje.setIdMensaje(1L);
        mensaje.setContenido("Hola");
        mensaje.setIdEmisor(1L);
        mensaje.setIdReceptor(2L);
        mensaje.setFecha(LocalDateTime.now());

        when(repository.findById(1L))
                .thenReturn(Optional.of(mensaje));

        // WHEN
        MensajeriaResponseDTO resultado =
                service.buscarPorId(1L);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdMensaje());
        assertEquals("Hola", resultado.getContenido());

        verify(repository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_cuandoNoExiste_debeLanzarExcepcion() {

        // GIVEN
        when(repository.findById(99L))
                .thenReturn(Optional.empty());

        // WHEN + THEN
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.buscarPorId(99L)
                );

        assertEquals("Mensaje no encontrado",
                exception.getMessage());
    }

    @Test
    void guardar_cuandoDatosValidos_debeGuardarMensaje() {

        // GIVEN
        MensajeriaRequestDTO dto = new MensajeriaRequestDTO();
        dto.setContenido("Hola, ¿cómo estás?");
        dto.setIdEmisor(1L);
        dto.setIdReceptor(2L);
        /*
         * Render standalone:
         * Validaciones remotas comentadas porque MS-Mensajeria se despliega solo en Render.
         * Reactivar cuando Feign vuelva a estar habilitado.
         *
         * when(inquilinoClient.validarInquilino(1L))
         *         .thenReturn(true);
         *
         * when(inquilinoClient.validarInquilino(2L))
         *         .thenReturn(true);
         *
         * when(reservaClient.buscarReserva(1))
         *         .thenReturn(new Object());
         */

        Mensajeria guardado = new Mensajeria();
        guardado.setIdMensaje(1L);
        guardado.setContenido("Hola, ¿cómo estás?");
        guardado.setIdEmisor(1L);
        guardado.setIdReceptor(2L);
        guardado.setFecha(LocalDateTime.now());

        when(repository.save(any(Mensajeria.class)))
                .thenReturn(guardado);

        // WHEN
        MensajeriaResponseDTO resultado =
                service.guardar(dto);

        // THEN
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdMensaje());
        assertEquals("Hola, ¿cómo estás?",
                resultado.getContenido());
        /*
         * Render standalone:
         * Verificaciones Feign suspendidas mientras el CRUD trabaja solo con la BD local.
         *
         * verify(inquilinoClient, times(1))
         *         .validarInquilino(1L);
         *
         * verify(inquilinoClient, times(1))
         *         .validarInquilino(2L);
         *
         * verify(reservaClient, times(1))
         *         .buscarReserva(1);
         */
        verifyNoInteractions(inquilinoClient, reservaClient);

        verify(repository, times(1))
                .save(any(Mensajeria.class));
    }

    @Test
    void guardar_cuandoEmisorYReceptorSonIguales_debeLanzarExcepcion() {

        // GIVEN
        MensajeriaRequestDTO dto = new MensajeriaRequestDTO();
        dto.setContenido("Hola");
        dto.setIdEmisor(1L);
        dto.setIdReceptor(1L);

        // WHEN + THEN
        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () -> service.guardar(dto)
                );

        assertEquals(
                "No puedes enviarte mensajes a ti mismo",
                exception.getMessage()
        );

        verify(repository, never())
                .save(any(Mensajeria.class));
    }

    @Test
    void actualizar_cuandoExiste_debeActualizarMensaje() {

        // GIVEN
        Mensajeria existente = new Mensajeria();
        existente.setIdMensaje(1L);
        existente.setContenido("Mensaje antiguo");
        existente.setIdEmisor(1L);
        existente.setIdReceptor(2L);
        existente.setFecha(LocalDateTime.now());

        MensajeriaRequestDTO dto = new MensajeriaRequestDTO();
        dto.setContenido("Mensaje actualizado");
        dto.setIdEmisor(1L);
        dto.setIdReceptor(2L);

        Mensajeria actualizado = new Mensajeria();
        actualizado.setIdMensaje(1L);
        actualizado.setContenido("Mensaje actualizado");
        actualizado.setIdEmisor(1L);
        actualizado.setIdReceptor(2L);
        actualizado.setFecha(LocalDateTime.now());

        when(repository.findById(1L))
                .thenReturn(Optional.of(existente));

        when(repository.save(any(Mensajeria.class)))
                .thenReturn(actualizado);

        // WHEN
        MensajeriaResponseDTO resultado =
                service.actualizar(1L, dto);

        // THEN
        assertEquals("Mensaje actualizado",
                resultado.getContenido());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1))
                .save(any(Mensajeria.class));
    }

    @Test
    void eliminar_cuandoExiste_debeEliminarMensaje() {

        // GIVEN
        Mensajeria mensaje = new Mensajeria();
        mensaje.setIdMensaje(1L);
        mensaje.setContenido("Hola");
        mensaje.setIdEmisor(1L);
        mensaje.setIdReceptor(2L);
        mensaje.setFecha(LocalDateTime.now());

        when(repository.findById(1L))
                .thenReturn(Optional.of(mensaje));

        // WHEN
        service.eliminar(1L);

        // THEN
        verify(repository, times(1)).delete(mensaje);
    }

    @Test
    void buscarPorEmisor_debeRetornarLista() {

        // GIVEN
        Mensajeria mensaje = new Mensajeria();
        mensaje.setIdMensaje(1L);
        mensaje.setContenido("Hola");
        mensaje.setIdEmisor(1L);
        mensaje.setIdReceptor(2L);
        mensaje.setFecha(LocalDateTime.now());

        when(repository.findByIdEmisorOrderByFechaDesc(1L))
                .thenReturn(List.of(mensaje));

        // WHEN
        List<MensajeriaResponseDTO> resultado =
                service.buscarPorEmisor(1L);

        // THEN
        assertEquals(1, resultado.size());
        assertEquals(1L, resultado.get(0).getIdEmisor());
    }

    @Test
    void buscarPorReceptor_debeRetornarLista() {

        // GIVEN
        Mensajeria mensaje = new Mensajeria();
        mensaje.setIdMensaje(1L);
        mensaje.setContenido("Hola");
        mensaje.setIdEmisor(1L);
        mensaje.setIdReceptor(2L);
        mensaje.setFecha(LocalDateTime.now());

        when(repository.findByIdReceptorOrderByFechaDesc(2L))
                .thenReturn(List.of(mensaje));

        // WHEN
        List<MensajeriaResponseDTO> resultado =
                service.buscarPorReceptor(2L);

        // THEN
        assertEquals(1, resultado.size());
        assertEquals(2L, resultado.get(0).getIdReceptor());
    }

    @Test
    void buscarConversacion_debeRetornarLista() {

        // GIVEN
        Mensajeria mensaje = new Mensajeria();
        mensaje.setIdMensaje(1L);
        mensaje.setContenido("Hola");
        mensaje.setIdEmisor(1L);
        mensaje.setIdReceptor(2L);
        mensaje.setFecha(LocalDateTime.now());

        when(repository.obtenerConversacion(1L, 2L))
                .thenReturn(List.of(mensaje));

        // WHEN
        List<MensajeriaResponseDTO> resultado =
                service.buscarConversacion(1L, 2L);

        // THEN
        assertEquals(1, resultado.size());
        assertEquals("Hola", resultado.get(0).getContenido());
    }
}

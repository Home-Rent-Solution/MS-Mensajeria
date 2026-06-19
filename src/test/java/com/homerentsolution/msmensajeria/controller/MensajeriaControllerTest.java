package com.homerentsolution.msmensajeria.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homerentsolution.msmensajeria.dto.MensajeriaRequestDTO;
import com.homerentsolution.msmensajeria.dto.MensajeriaResponseDTO;
import com.homerentsolution.msmensajeria.service.MensajeriaService;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MensajeriaController.class)
class MensajeriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MensajeriaService service;

    @Test
    void listar_debeRetornar200() throws Exception {

        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(1L);
        response.setContenido("Hola");
        response.setIdEmisor(1L);
        response.setIdReceptor(2L);
        response.setFecha(LocalDateTime.now());

        when(service.listar())
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/v1/mensajes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idMensaje")
                        .value(1))
                .andExpect(jsonPath("$[0].contenido")
                        .value("Hola"));

        verify(service).listar();
    }

    @Test
    void buscar_debeRetornar200() throws Exception {

        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(1L);
        response.setContenido("Hola");
        response.setIdEmisor(1L);
        response.setIdReceptor(2L);
        response.setFecha(LocalDateTime.now());

        when(service.buscarPorId(1L))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/mensajes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idMensaje")
                        .value(1));

        verify(service).buscarPorId(1L);
    }

    @Test
    void guardar_debeRetornar201() throws Exception {

        MensajeriaRequestDTO request =
                new MensajeriaRequestDTO();

        request.setContenido("Hola");
        request.setIdEmisor(1L);
        request.setIdReceptor(2L);

        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(1L);
        response.setContenido("Hola");
        response.setIdEmisor(1L);
        response.setIdReceptor(2L);
        response.setFecha(LocalDateTime.now());

        when(service.guardar(any(MensajeriaRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/mensajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMensaje")
                        .value(1));

        verify(service).guardar(any(MensajeriaRequestDTO.class));
    }

    @Test
    void guardar_conDatosInvalidos_debeRetornar400()
            throws Exception {

        MensajeriaRequestDTO request =
                new MensajeriaRequestDTO();

        request.setContenido("");
        request.setIdEmisor(1L);
        request.setIdReceptor(2L);

        mockMvc.perform(post("/api/v1/mensajes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(service, never()).guardar(any());
    }

    @Test
    void actualizar_debeRetornar200() throws Exception {

        MensajeriaRequestDTO request =
                new MensajeriaRequestDTO();

        request.setContenido("Mensaje actualizado");
        request.setIdEmisor(1L);
        request.setIdReceptor(2L);

        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(1L);
        response.setContenido("Mensaje actualizado");
        response.setIdEmisor(1L);
        response.setIdReceptor(2L);
        response.setFecha(LocalDateTime.now());

        when(service.actualizar(
                eq(1L),
                any(MensajeriaRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/mensajes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contenido")
                        .value("Mensaje actualizado"));

        verify(service)
                .actualizar(eq(1L),
                        any(MensajeriaRequestDTO.class));
    }

    @Test
    void eliminar_debeRetornar204() throws Exception {

        doNothing().when(service)
                .eliminar(1L);

        mockMvc.perform(delete("/api/v1/mensajes/1"))
                .andExpect(status().isNoContent());

        verify(service).eliminar(1L);
    }

    @Test
    void buscarPorEmisor_debeRetornar200() throws Exception {

        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(1L);
        response.setContenido("Hola");
        response.setIdEmisor(1L);
        response.setIdReceptor(2L);
        response.setFecha(LocalDateTime.now());

        when(service.buscarPorEmisor(1L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/v1/mensajes/emisor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEmisor")
                        .value(1));

        verify(service).buscarPorEmisor(1L);
    }

    @Test
    void buscarPorReceptor_debeRetornar200() throws Exception {

        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(1L);
        response.setContenido("Hola");
        response.setIdEmisor(1L);
        response.setIdReceptor(2L);
        response.setFecha(LocalDateTime.now());

        when(service.buscarPorReceptor(2L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/v1/mensajes/receptor/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReceptor")
                        .value(2));

        verify(service).buscarPorReceptor(2L);
    }

    @Test
    void buscarConversacion_debeRetornar200()
            throws Exception {

        MensajeriaResponseDTO response =
                new MensajeriaResponseDTO();

        response.setIdMensaje(1L);
        response.setContenido("Hola");
        response.setIdEmisor(1L);
        response.setIdReceptor(2L);
        response.setFecha(LocalDateTime.now());

        when(service.buscarConversacion(1L, 2L))
                .thenReturn(List.of(response));

        mockMvc.perform(
                        get("/api/v1/mensajes/conversacion")
                                .param("emisor", "1")
                                .param("receptor", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].contenido")
                        .value("Hola"));

        verify(service)
                .buscarConversacion(1L, 2L);
    }
}
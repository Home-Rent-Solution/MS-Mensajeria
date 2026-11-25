package com.homerentsolution.msmensajeria.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Prueba el formulario de contacto de punta a punta, con la base H2 de las pruebas
@SpringBootTest
@AutoConfigureMockMvc
class ContactoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private String contacto(String nombre, String correo, String telefono, String motivo, String mensaje) {
        return """
                {"nombre":"%s","correo":"%s","telefono":"%s","motivo":"%s","mensaje":"%s"}
                """.formatted(nombre, correo, telefono, motivo, mensaje);
    }

    @Test
    void enviar_conDatosValidos_guardaElMensaje() throws Exception {
        mockMvc.perform(post("/api/v1/contacto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contacto("María Soto", "Maria.Soto@gmail.com", "+56 9 1234 5678",
                                "consulta", "Quisiera saber si la cabaña tiene estacionamiento.")))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idContacto").isNotEmpty())
                .andExpect(jsonPath("$.correo").value("maria.soto@gmail.com"))
                .andExpect(jsonPath("$.fecha").isNotEmpty());
    }

    @Test
    void enviar_sinTelefono_tambienSeAcepta() throws Exception {
        mockMvc.perform(post("/api/v1/contacto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contacto("Pedro Pérez", "pedro@gmail.com", "",
                                "sugerencia", "Me gustaría ver más cabañas en el sur.")))
                .andExpect(status().isCreated());
    }

    @Test
    void enviar_conMotivoInvalido_responde400() throws Exception {
        mockMvc.perform(post("/api/v1/contacto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contacto("María Soto", "maria@gmail.com", "",
                                "spam", "Un mensaje con un motivo que no existe.")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enviar_conMensajeMuyCorto_responde400() throws Exception {
        mockMvc.perform(post("/api/v1/contacto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contacto("María Soto", "maria@gmail.com", "", "otro", "Hola")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void enviar_conTelefonoDe5Digitos_responde400() throws Exception {
        mockMvc.perform(post("/api/v1/contacto")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contacto("María Soto", "maria@gmail.com", "12345",
                                "otro", "Un mensaje con un teléfono muy corto.")))
                .andExpect(status().isBadRequest());
    }

    @Test
    void listar_devuelveLosMensajes() throws Exception {
        mockMvc.perform(post("/api/v1/contacto")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contacto("Ana Díaz", "ana@gmail.com", "", "reserva",
                        "Necesito ayuda para cambiar las fechas de mi reserva.")));

        mockMvc.perform(get("/api/v1/contacto"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").exists());
    }

    @Test
    void eliminar_unMensajeQueNoExiste_responde400() throws Exception {
        mockMvc.perform(delete("/api/v1/contacto/9999"))
                .andExpect(status().isBadRequest());
    }
}

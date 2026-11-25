package com.homerentsolution.msmensajeria.service;

import com.homerentsolution.msmensajeria.dto.ContactoRequestDTO;
import com.homerentsolution.msmensajeria.dto.ContactoResponseDTO;
import com.homerentsolution.msmensajeria.model.MensajeContacto;
import com.homerentsolution.msmensajeria.repository.MensajeContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ContactoService {

    private final MensajeContactoRepository repository;

    @Autowired
    public ContactoService(MensajeContactoRepository repository) {
        this.repository = repository;
    }

    public ContactoResponseDTO guardar(ContactoRequestDTO dto) {
        MensajeContacto contacto = new MensajeContacto();
        contacto.setNombre(dto.getNombre().trim());
        contacto.setCorreo(dto.getCorreo().trim().toLowerCase());
        contacto.setTelefono(dto.getTelefono() == null || dto.getTelefono().isBlank() ? null : dto.getTelefono().trim());
        contacto.setMotivo(dto.getMotivo());
        contacto.setMensaje(dto.getMensaje().trim());
        contacto.setFecha(LocalDateTime.now());
        return aResponse(repository.save(contacto));
    }

    public List<ContactoResponseDTO> listar() {
        return repository.findAllByOrderByFechaDesc().stream()
                .map(this::aResponse)
                .toList();
    }

    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("El mensaje de contacto con ID: " + id + " no existe");
        }
        repository.deleteById(id);
    }

    private ContactoResponseDTO aResponse(MensajeContacto contacto) {
        return new ContactoResponseDTO(
                contacto.getIdContacto(),
                contacto.getNombre(),
                contacto.getCorreo(),
                contacto.getTelefono(),
                contacto.getMotivo(),
                contacto.getMensaje(),
                contacto.getFecha()
        );
    }
}

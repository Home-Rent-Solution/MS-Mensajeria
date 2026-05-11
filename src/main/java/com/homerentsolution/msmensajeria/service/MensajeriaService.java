package com.homerentsolution.msmensajeria.service;

import com.homerentsolution.msmensajeria.model.Mensajeria;
import com.homerentsolution.msmensajeria.repository.MensajeriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeriaService {

    @Autowired
    private MensajeriaRepository repository;

    // el CRUD completo
    public List<Mensajeria> listar() {
        return repository.findAll();
    }

    public Mensajeria guardar(Mensajeria mensaje) {

        // Regla de negocio
        if (mensaje.getIdEmisor().equals(mensaje.getIdReceptor())) {
            throw new RuntimeException("No puedes enviarte mensajes a ti mismo");
        }
        //actualizado fecha automática
        mensaje.setFecha(LocalDateTime.now());
        return repository.save(mensaje);
    }
    public Mensajeria buscarPorId(Long id){
        return repository.findById(id).orElse(null);
    }
    public Mensajeria actualizar(Long id, Mensajeria mensajeActualizado) {
        Mensajeria mensaje = repository.findById(id).orElse(null);

        if (mensaje != null) {
            mensaje.setContenido(mensajeActualizado.getContenido());
            mensaje.setIdEmisor(mensajeActualizado.getIdEmisor());
            mensaje.setIdReceptor(mensajeActualizado.getIdReceptor());
            return repository.save(mensaje);
        }
        return null;
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }


}
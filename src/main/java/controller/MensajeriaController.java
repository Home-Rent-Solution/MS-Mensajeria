package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import service.MensajeriaService;
import model.Mensajeria;

@RestController
@RequestMapping("/api/v1/mensajes")
public class MensajeriaController {
    @Autowired
    private MensajeriaService service;

    @GetMapping
    public List<Mensajeria> listar() {
        return service.listar();
    }

    @PostMapping
    public Mensajeria guardar(@RequestBody Mensajeria mensaje) {
        return service.guardar(mensaje);
    }
}

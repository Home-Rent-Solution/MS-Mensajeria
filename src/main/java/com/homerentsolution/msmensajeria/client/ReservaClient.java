package com.homerentsolution.msmensajeria.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * Render standalone:
 * La integración Feign queda comentada para que MS-Mensajeria no intente
 * comunicarse con MS-Reservas cuando Render levante solo este servicio.
 *
 * Código original para reactivar integración:
 *
 * import org.springframework.cloud.openfeign.FeignClient;
 *
 * @FeignClient(
 *         name = "ms-reservas"
 * )
 */
public interface ReservaClient {

    @GetMapping("/api/v1/reservas/{id}/cliente")
    Object buscarReserva(
            @PathVariable int id
    );
}
package com.homerentsolution.msmensajeria.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * Render standalone:
 * La integración Feign queda comentada para que MS-Mensajeria no intente
 * comunicarse con MS-Inquilinos cuando Render levante solo este servicio.
 *
 * Código original para reactivar integración:
 *
 * import org.springframework.cloud.openfeign.FeignClient;
 *
 * @FeignClient(
 *         name = "ms-inquilinos"
 * )
 */
public interface InquilinoClient {

    @GetMapping(
            "/api/v1/inquilinos/{idInquilino}/validar"
    )
    Boolean validarInquilino(
            @PathVariable Long idInquilino
    );
}
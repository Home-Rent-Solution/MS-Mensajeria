package com.homerentsolution.msmensajeria.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "ms-inquilinos"
)
public interface InquilinoClient {

    @GetMapping(
            "/api/v1/inquilinos/{idInquilino}/validar"
    )
    Boolean validarInquilino(
            @PathVariable Long idInquilino
    );
}
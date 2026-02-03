package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.dacs.bff.dto.TipoIntervencionDto;

@FeignClient(name = "apiBackEndTipoIntervencionesClient", url = "${feign.client.config.apiBackEndTipoIntervencionesClient.url}")
public interface ApiBackendTipoIntervenciones {

    @GetMapping("/tipos-intervenciones")
    ResponseEntity<List<TipoIntervencionDto>> getTipoIntervenciones();
}

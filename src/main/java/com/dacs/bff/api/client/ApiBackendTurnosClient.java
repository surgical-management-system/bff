package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.TurnoDto;

@FeignClient(name = "apiBackendTurnosClient", url = "${feign.client.config.apiBackendTurnosClient.url}", configuration = FeignConfig.class)


public interface ApiBackendTurnosClient {

    @GetMapping("/turnos")
    public ResponseEntity<PaginacionDto.Response<TurnoDto>> getTurnosDisponibles(PaginacionDto.Turnos paginacion);

}

package com.dacs.bff.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.TurnoDto;

@FeignClient(name = "apiBackendTurnosClient", url = "${feign.client.config.apiBackendTurnosClient.url}", configuration = FeignConfig.class)

public interface ApiBackendTurnosClient {

    @GetMapping("/turnos")
    public ResponseEntity<PaginacionDto.Response<TurnoDto>> getTurnosDisponibles(@RequestParam Integer pagina,
            @RequestParam Integer tamano,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin, 
            @RequestParam(required = false) Integer quirofanoId,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) Integer duracionMinutos,
            @RequestParam(required = false) Long servicioId);

    @PostMapping("/turnos/generar-turnos")
    public ResponseEntity<Void> generarTurnos(@RequestBody String entity);
}

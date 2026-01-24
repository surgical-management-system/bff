package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PaginacionDto;

@FeignClient(name = "apiBackendPacientesClient", url = "${feign.client.config.apiBackendPacientesClient.url}", configuration = FeignConfig.class)

public interface ApiBackendPacientesClient {

    @GetMapping("/pacientes")          
    List<PacienteDto.BackResponse> pacientes(@RequestParam(name = "search", required = false) List<Long> pacienteIds);

    @PostMapping("/pacientes")
    PacienteDto.BackResponse save(@RequestBody PacienteDto.BackResponse paciente);

    @PutMapping("/pacientes")
    PacienteDto.BackResponse update(@RequestBody PacienteDto.BackResponse paciente);

    @DeleteMapping("/pacientes/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);

    @GetMapping("/pacientes")
    PaginacionDto.Response<PacienteDto.BackResponse> getPacientes(
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "search", required = false) String search
    );

}

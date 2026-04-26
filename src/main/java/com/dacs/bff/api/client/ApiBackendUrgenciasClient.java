package com.dacs.bff.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.UrgenciaDTO;

@FeignClient(name = "apiBackendUrgenciasClient", url = "${feign.client.config.apiBackendUrgenciasClient.url}", configuration = FeignConfig.class)
public interface ApiBackendUrgenciasClient {

    @GetMapping("/urgencia")
    PaginacionDto.Response<UrgenciaDTO.BackResponse> getUrgencias(@RequestParam(name = "pagina", required = false) Integer pagina,
            @RequestParam(name = "tamano", required = false) Integer tamano,
            @RequestParam(name = "fechaInicio", required = false) String fechaInicio,
            @RequestParam(name = "fechaFin", required = false) String fechaFin,
            @RequestParam(name = "estado", required = false) String estado,
            @RequestParam(name = "search", required = false) String search,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "order", required = false) String order);

    @PostMapping("/urgencia")
    ResponseEntity<UrgenciaDTO.BackResponse> create(@RequestBody UrgenciaDTO.FrontRequest urgencia);

    @PutMapping("/urgencia/{id}")
    ResponseEntity<UrgenciaDTO.BackResponse> update(@PathVariable("id") String id,
            @RequestBody UrgenciaDTO.FrontRequest urgencia);

    @DeleteMapping("/urgencia/{id}")
    ResponseEntity<Void> delete(@PathVariable("id") Long id);
}
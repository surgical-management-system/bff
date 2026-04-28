package com.dacs.bff.api.client;

import java.util.List;

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
import com.dacs.bff.dto.MiembroEquipoDTO;
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

        @PutMapping("/urgencia/{id}/inicializar")
        ResponseEntity<UrgenciaDTO.BackResponse> inicializarUrgencia(@PathVariable("id") Long id);

        @PutMapping("/urgencia/{id}/finalizar")
        ResponseEntity<UrgenciaDTO.BackResponse> finalizarUrgencia(@PathVariable("id") Long id);

        @GetMapping("/urgencia/{id}/equipo-medico")
        ResponseEntity<List<MiembroEquipoDTO.BackResponse>> getEquipoMedico(@PathVariable("id") Long id);

        @PostMapping("/urgencia/{id}/equipo-medico")
        ResponseEntity<List<MiembroEquipoDTO.BackResponse>> saveEquipoMedico(@PathVariable("id") Long id,
                        @RequestBody List<MiembroEquipoDTO.Create> miembros);

        @GetMapping("/urgencia/{urgenciaId}/intervenciones")
        ResponseEntity<List<com.dacs.bff.dto.IntervencionDto>> getIntervencionesByUrgenciaId(@PathVariable("urgenciaId") Long urgenciaId);

        @PostMapping("/urgencia/{urgenciaId}/intervenciones")
        ResponseEntity<com.dacs.bff.dto.IntervencionDto> createIntervencionForUrgencia(@PathVariable("urgenciaId") Long urgenciaId,
                        @RequestBody com.dacs.bff.dto.IntervencionDto intervencion);

        @PutMapping("/urgencia/{urgenciaId}/intervenciones/{intervencionId}")
        ResponseEntity<com.dacs.bff.dto.IntervencionDto> updateIntervencionForUrgencia(@PathVariable("urgenciaId") Long urgenciaId,
                        @PathVariable("intervencionId") Long intervencionId,
                        @RequestBody com.dacs.bff.dto.IntervencionDto intervencion);

        @DeleteMapping("/urgencia/{urgenciaId}/intervenciones/{intervencionId}")
        ResponseEntity<Void> deleteIntervencionForUrgencia(@PathVariable("urgenciaId") Long urgenciaId,
                        @PathVariable("intervencionId") Long intervencionId);
}
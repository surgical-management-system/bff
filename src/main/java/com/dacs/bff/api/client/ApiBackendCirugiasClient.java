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
import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.IntervencionDto;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.ServicioDto;

@FeignClient(name = "apiBackendCirugiasClient", url = "${feign.client.config.apiBackendCirugiasClient.url}", configuration = FeignConfig.class)

public interface ApiBackendCirugiasClient {

        @GetMapping("/ping")
        String ping();

        @GetMapping("/cirugia")
        PaginacionDto.Response<CirugiaDTO.BackResponse> getCirugias(@RequestParam(name = "pagina", required = false) Integer pagina,
                        @RequestParam(name = "tamano", required = false) Integer tamano,
                        @RequestParam(name = "fechaInicio", required = false) String fechaInicio,
                        @RequestParam(name = "fechaFin", required = false) String fechaFin,
                        @RequestParam(name = "estado", required = false) String estado,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "sort", required = false) String sort,
                        @RequestParam(name = "order", required = false) String order);

        @PostMapping("/cirugia")
        ResponseEntity<CirugiaDTO.BackResponse> create(@RequestBody CirugiaDTO.FrontRequest cirugia);

        @PutMapping("/cirugia/{id}")
        ResponseEntity<CirugiaDTO.BackResponse> update(@PathVariable("id") String id,
                        @RequestBody CirugiaDTO.FrontRequest cirugia);

        @DeleteMapping("/cirugia/{id}")
        ResponseEntity<Void> delete(@PathVariable("id") Long id);

        @GetMapping("/cirugia/{id}/equipo-medico")
        ResponseEntity<List<MiembroEquipoDTO.BackResponse>> getEquipoMedico(@PathVariable("id") Long id);

        @PostMapping("/cirugia/{id}/equipo-medico")
        ResponseEntity<List<MiembroEquipoDTO.BackResponse>> saveEquipoMedico(@PathVariable("id") Long id,
                        @RequestBody List<MiembroEquipoDTO.Create> miembros);

        @GetMapping("/cirugia/servicios")
        ResponseEntity<List<ServicioDto>> getServicios(@RequestParam(name = "tamano", required = false) int tamano,
                        @RequestParam(name = "pagina", required = false) int pagina);

        @GetMapping("/cirugia/{cirugiaId}/intervenciones")
        ResponseEntity<List<IntervencionDto>> getIntervencionesByCirugiaId(@PathVariable("cirugiaId") Long cirugiaId);

        @PostMapping("/cirugia/{cirugiaId}/intervenciones")
        ResponseEntity<IntervencionDto> createIntervencion(@PathVariable("cirugiaId") Long cirugiaId,
                        @RequestBody IntervencionDto intervencion);

        @PutMapping("/cirugia/{cirugiaId}/intervenciones/{intervencionId}")
        ResponseEntity<IntervencionDto> updateIntervencion(@PathVariable("cirugiaId") Long cirugiaId,
                        @PathVariable("intervencionId") Long intervencionId,
                        @RequestBody IntervencionDto intervencion);

        @DeleteMapping("/cirugia/{cirugiaId}/intervenciones/{intervencionId}")
        ResponseEntity<Void> deleteIntervencion(@PathVariable("cirugiaId") Long cirugiaId,
                        @PathVariable("intervencionId") Long intervencionId);

        @PutMapping("/cirugia/{id}/finalizar")
        ResponseEntity<CirugiaDTO.BackResponse> finalizarCirugia(@PathVariable("id") Long id);
}

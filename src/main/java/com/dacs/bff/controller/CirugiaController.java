package com.dacs.bff.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.IntervencionDto;
import com.dacs.bff.dto.ServicioDto;
// import com.dacs.bff.dto.CirugiaPageResponse;
import com.dacs.bff.service.ApiBackendCirugiaService;
import com.dacs.bff.util.ApiResponseBuilder;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@RestController
@RequestMapping("/cirugias")
public class CirugiaController {

    @Autowired
    private ApiBackendCirugiaService cirugiaService;

    @GetMapping("") // Agregar para manejar filtrado por otros campos
    public ResponseEntity<ApiResponse<PaginacionDto.Response<CirugiaDTO.FrontResponse>>> getAll(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamano", defaultValue = "20") int tamano,
            @RequestParam(value = "fechaInicio", required = false) String fechaInicio,
            @RequestParam(value = "fechaFin", required = false) String fechaFin) {
        try {
            PaginacionDto.Response<CirugiaDTO.FrontResponse> resp = cirugiaService.getCirugias(pagina, tamano, fechaInicio, fechaFin);  
            return ApiResponseBuilder.okWithPagination(resp);
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener cirugías: " + e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<CirugiaDTO.FrontResponse>> create(@RequestBody CirugiaDTO.FrontRequest cirugiaDTO)
            throws Exception {
        try {
            ResponseEntity<CirugiaDTO.FrontResponse> response = cirugiaService.createCirugia(cirugiaDTO);
            return ApiResponseBuilder.created(response.getBody(), "Cirugia creada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al crear la cirugía: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CirugiaDTO.FrontResponse>> update(@PathVariable String id,
            @RequestBody CirugiaDTO.FrontRequest cirugiaDTO) throws Exception {
        try {
            ResponseEntity<CirugiaDTO.FrontResponse> data = cirugiaService.updateCirugia(id, cirugiaDTO);
            return ApiResponseBuilder.ok(data.getBody(), "Cirugia actualizada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al actualizar la cirugía: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable @NotNull Long id) {
        try {
            cirugiaService.deleteCirugia(id);
            return ApiResponseBuilder.ok(null, "Cirugia eliminada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al eliminar la cirugia: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO.Response>>> getEquipoMedico(@PathVariable Long id) {
        try {
            ResponseEntity<List<MiembroEquipoDTO.Response>> response = cirugiaService.getEquipoMedico(id);
            return ApiResponseBuilder.ok(response.getBody());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener equipo médico: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO.Response>>> postEquipoMedico(@PathVariable Long id,
            @RequestBody List<MiembroEquipoDTO.Create> miembros) {
        try {
            ResponseEntity<List<MiembroEquipoDTO.Response>> response = cirugiaService.saveEquipoMedico(miembros, id);
            return ApiResponseBuilder.ok(response.getBody(), "Equipo medico guardado exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al guardar equipo médico: " + e.getMessage());
        }
    }

    @GetMapping("/servicios")
    public ResponseEntity<ApiResponse<List<ServicioDto>>> getServicios(@RequestParam(defaultValue = "10") int tamano,
            @RequestParam(defaultValue = "0") int pagina) {
        try {
            ResponseEntity<List<ServicioDto>> servicios = cirugiaService.getServicios(tamano, pagina);
            return ApiResponseBuilder.ok(servicios.getBody());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener servicios: " + e.getMessage());
        }
    }

    @PostMapping("{id}/finalizar")
    public ResponseEntity<ApiResponse<CirugiaDTO.FrontResponse>> finalizarCirugia(@RequestBody IntervencionDto entity,
            @PathVariable Long id
    ) {
        
        
        return null;
    }

    @GetMapping("/{cirugiaId}/intervenciones")
    public ResponseEntity<ApiResponse<List<IntervencionDto>>> getIntervencionesByCirugiaId(@PathVariable Long cirugiaId) {
        try {
            ResponseEntity<List<IntervencionDto>> response = cirugiaService.getIntervencionesByCirugiaId(cirugiaId);
            return ApiResponseBuilder.ok(response.getBody());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener intervenciones: " + e.getMessage());
        }
    }

    @PostMapping("/{cirugiaId}/intervenciones")
    public ResponseEntity<ApiResponse<IntervencionDto>> createIntervencion(@PathVariable Long cirugiaId,
            @RequestBody IntervencionDto intervencion) {
        try {
            ResponseEntity<IntervencionDto> response = cirugiaService.createIntervencion(cirugiaId, intervencion);
            return ApiResponseBuilder.created(response.getBody(), "Intervención creada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al crear intervención: " + e.getMessage());
        }
    }

    @PutMapping("/{cirugiaId}/intervenciones/{intervencionId}")
    public ResponseEntity<ApiResponse<IntervencionDto>> updateIntervencion(@PathVariable Long cirugiaId,
            @PathVariable Long intervencionId, @RequestBody IntervencionDto intervencion) {
        try {
            ResponseEntity<IntervencionDto> response = cirugiaService.updateIntervencion(cirugiaId, intervencionId, intervencion);
            return ApiResponseBuilder.ok(response.getBody(), "Intervención actualizada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al actualizar intervención: " + e.getMessage());
        }
    }

    @DeleteMapping("/{cirugiaId}/intervenciones/{intervencionId}")
    public ResponseEntity<ApiResponse<Void>> deleteIntervencion(@PathVariable Long cirugiaId,
            @PathVariable Long intervencionId) {
        try {
            cirugiaService.deleteIntervencion(cirugiaId, intervencionId);
            return ApiResponseBuilder.ok(null, "Intervención eliminada exitosamente");
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al eliminar intervención: " + e.getMessage());
        }
    }
}

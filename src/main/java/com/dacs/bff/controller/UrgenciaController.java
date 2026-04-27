package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.UrgenciaDTO;
import com.dacs.bff.exeption.BackendApiException;
import com.dacs.bff.service.ApiBackendUrgenciaService;
import com.dacs.bff.util.ApiResponseBuilder;

@Validated
@RestController
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
@RequestMapping({ "/urgencias", "/urgencia" })
public class UrgenciaController {

    @Autowired
    private ApiBackendUrgenciaService urgenciaService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<PaginacionDto.Response<UrgenciaDTO.FrontResponse>>> getAll(
            @RequestParam(value = "pagina", defaultValue = "0") int pagina,
            @RequestParam(value = "tamano", defaultValue = "16") int tamano,
            @RequestParam(value = "fechaInicio", required = false) String fechaInicio,
            @RequestParam(value = "fechaFin", required = false) String fechaFin,
            @RequestParam(value = "estado", required = false) String estado,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sort", required = false, defaultValue = "fechaHoraInicio") String sort,
            @RequestParam(value = "order", required = false, defaultValue = "asc") String order) {
        try {
            PaginacionDto.Response<UrgenciaDTO.FrontResponse> resp = urgenciaService.getUrgencias(pagina, tamano, fechaInicio, fechaFin, estado, search, sort, order);
            return ApiResponseBuilder.okWithPagination(resp);
        } catch (BackendApiException e) {
            return ApiResponseBuilder.buildResponse(null, e.getStatus(), e.getDescription());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener urgencias: " + e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<UrgenciaDTO.FrontResponse>> create(@RequestBody UrgenciaDTO.FrontRequest urgencia)
            throws Exception {
        try {
            ResponseEntity<UrgenciaDTO.FrontResponse> response = urgenciaService.createUrgencia(urgencia);
            return ApiResponseBuilder.created(response.getBody(), "Urgencia creada exitosamente");
        } catch (BackendApiException e) {
            return ApiResponseBuilder.buildResponse(null, e.getStatus(), e.getDescription());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al crear urgencia: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UrgenciaDTO.FrontResponse>> update(@PathVariable String id,
            @RequestBody UrgenciaDTO.FrontRequest urgencia) throws Exception {
        try {
            ResponseEntity<UrgenciaDTO.FrontResponse> response = urgenciaService.updateUrgencia(id, urgencia);
            return ApiResponseBuilder.ok(response.getBody(), "Urgencia actualizada exitosamente");
        } catch (BackendApiException e) {
            return ApiResponseBuilder.buildResponse(null, e.getStatus(), e.getDescription());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al actualizar urgencia: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/inicializar")
    public ResponseEntity<ApiResponse<UrgenciaDTO.FrontResponse>> inicializarUrgencia(@PathVariable Long id) {
        try {
            ResponseEntity<UrgenciaDTO.FrontResponse> response = urgenciaService.inicializarUrgencia(id);
            return ApiResponseBuilder.ok(response.getBody(), "Urgencia inicializada exitosamente");
        } catch (BackendApiException e) {
            return ApiResponseBuilder.buildResponse(null, e.getStatus(), e.getDescription());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al inicializar urgencia: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            urgenciaService.deleteUrgencia(id);
            return ApiResponseBuilder.ok(null, "Urgencia eliminada exitosamente");
        } catch (BackendApiException e) {
            return ApiResponseBuilder.buildResponse(null, e.getStatus(), e.getDescription());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al eliminar urgencia: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO.Response>>> getEquipoMedico(@PathVariable Long id) {
        try {
            ResponseEntity<List<MiembroEquipoDTO.Response>> response = urgenciaService.getEquipoMedico(id);
            return ApiResponseBuilder.ok(response.getBody());
        } catch (BackendApiException e) {
            return ApiResponseBuilder.buildResponse(null, e.getStatus(), e.getDescription());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener equipo médico: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/equipo-medico")
    public ResponseEntity<ApiResponse<List<MiembroEquipoDTO.Response>>> postEquipoMedico(@PathVariable Long id,
            @RequestBody List<MiembroEquipoDTO.Create> miembros) {
        try {
            ResponseEntity<List<MiembroEquipoDTO.Response>> response = urgenciaService.saveEquipoMedico(miembros,
                    id);
            return ApiResponseBuilder.ok(response.getBody(), "Equipo medico guardado exitosamente");
        } catch (BackendApiException e) {
            return ApiResponseBuilder.buildResponse(null, e.getStatus(), e.getDescription());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al guardar equipo médico: " + e.getMessage());
        }
    }
}
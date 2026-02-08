package com.dacs.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.EstadisticasGeneralesDto;
import com.dacs.bff.service.ApiBackendDashboardService;
import com.dacs.bff.util.ApiResponseBuilder;

@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private ApiBackendDashboardService dashboardService;

    @GetMapping("/general")
    ResponseEntity<ApiResponse<EstadisticasGeneralesDto>> getEstadisticasGenerales() {
        try {
            ResponseEntity<EstadisticasGeneralesDto> estadisticas = dashboardService.getEstadisticasGenerales();
            return ApiResponseBuilder.ok(estadisticas.getBody());
        } catch (Exception e) {
            return ApiResponseBuilder.serverError("Error al obtener estadísticas generales: " + e.getMessage());
        }
    }
}

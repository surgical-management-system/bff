package com.dacs.bff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendDashboardClient;
import com.dacs.bff.dto.EstadisticasGeneralesDto;

@Service
public class ApiBackendDashboardServiceImpl implements ApiBackendDashboardService {

    @Autowired
    ApiBackendDashboardClient apiBackendDashboardClient;

    public ResponseEntity<EstadisticasGeneralesDto> getEstadisticasGenerales() {

        EstadisticasGeneralesDto estadisticas = apiBackendDashboardClient.getEstadisticasGenerales().getBody();
        return ResponseEntity.ok(estadisticas);
    }
}

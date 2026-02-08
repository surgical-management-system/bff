package com.dacs.bff.service;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.EstadisticasGeneralesDto;

public interface ApiBackendDashboardService {
    public ResponseEntity<EstadisticasGeneralesDto> getEstadisticasGenerales();
}

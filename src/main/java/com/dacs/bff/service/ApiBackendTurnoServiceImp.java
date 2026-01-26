package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendTurnosClient;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.TurnoDto;

@Service
public class ApiBackendTurnoServiceImp implements ApiBackendTurnoService {

    @Autowired
    private ApiBackendTurnosClient apiBackendTurnosClient;

    @Override
    public ResponseEntity<PaginacionDto.Response<TurnoDto>> getTurnosDisponibles(Integer pagina, Integer tamano, String fechaInicio, String fechaFin, Integer quirofanoId, String estado) {
        return apiBackendTurnosClient.getTurnosDisponibles(pagina, tamano, fechaInicio, fechaFin, quirofanoId, estado);
    }

    @Override
    public ResponseEntity<Void> generarTurnos(String entity) {
        return apiBackendTurnosClient.generarTurnos(entity);
    }
}

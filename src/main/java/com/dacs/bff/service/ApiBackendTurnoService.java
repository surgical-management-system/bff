package com.dacs.bff.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.PaginacionDto.Turnos;
import com.dacs.bff.dto.TurnoDto;

public interface ApiBackendTurnoService {
    
    public ResponseEntity<PaginacionDto.Response<TurnoDto>> getTurnosDisponibles(Turnos paginacion);

}

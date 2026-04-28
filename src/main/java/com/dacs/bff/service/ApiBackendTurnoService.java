package com.dacs.bff.service;


import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.TurnoDto;

public interface ApiBackendTurnoService {
    
    public ResponseEntity<PaginacionDto.Response<TurnoDto>> getTurnosDisponibles(Integer pagina, Integer tamano,
            String fechaInicio, String fechaFin, Integer quirofanoId, String estado, Integer duracionMinutos,
            Long servicioId);

    public ResponseEntity<Void> generarTurnos(String entity);
}

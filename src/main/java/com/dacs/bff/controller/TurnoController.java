package com.dacs.bff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.TurnoDto;
import com.dacs.bff.service.ApiBackendTurnoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@RestController
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private ApiBackendTurnoService turnosService;

    @GetMapping("")
    public ResponseEntity<PaginacionDto.Response<TurnoDto>> getTurnosDisponibles(
            @RequestParam(required = false, defaultValue = "0") Integer pagina,
            @RequestParam(required = false, defaultValue = "10") Integer tamano,
            @RequestParam(required = true) String fechaInicio,
            @RequestParam(required = true) String fechaFin,
            @RequestParam(required = false, defaultValue = "0") Integer quirofanoId,
            @RequestParam(required = false, defaultValue = "") String estado,
            @RequestParam(required = false) Integer duracionMinutos,
            @RequestParam(required = false) Integer duracion,
            @RequestParam(required = false) Integer duracionHoras,
            @RequestParam(required = false) Long servicioId) {
                try {
                    Integer duracionResuelta = duracionMinutos;
                    if (duracionResuelta == null && duracion != null) {
                        duracionResuelta = duracion;
                    }
                    if (duracionResuelta == null && duracionHoras != null) {
                        duracionResuelta = duracionHoras * 60;
                    }

                    ResponseEntity<PaginacionDto.Response<TurnoDto>> turnos = turnosService.getTurnosDisponibles(
                            pagina, tamano, fechaInicio, fechaFin, quirofanoId, estado, duracionResuelta,
                            servicioId);
                    return turnos;
                } catch (Exception e) {
                    log.error("Error al obtener turnos disponibles: {}", e.getMessage());
                    return ResponseEntity.status(500).build();
                }
    }


    // BORRAR
    @PostMapping("generar-turnos")
        @PreAuthorize("permitAll()")
    public ResponseEntity<Void> generarTurnos(@RequestBody String entity) {
        try {
            return turnosService.generarTurnos(entity);
        } catch (Exception e) {
            log.error("Error al generar turnos: {}", e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

}

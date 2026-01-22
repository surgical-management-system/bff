package com.dacs.bff.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.TurnoDto;
import com.dacs.bff.service.ApiBackendTurnoService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("/turnos")
public class TurnoController {

    @Autowired
    private ApiBackendTurnoService turnosService;

    @GetMapping("")
    public ResponseEntity<PaginacionDto.Response<TurnoDto>> getTurnosDisponibles(@RequestParam PaginacionDto.Turnos paginacion) {
    return turnosService.getTurnosDisponibles(paginacion);
    }

}

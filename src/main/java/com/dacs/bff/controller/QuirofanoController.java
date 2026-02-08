package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.dto.QuirofanoDTO;
import com.dacs.bff.service.ApiBackendQuirofanoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
@RequestMapping("/quirofano")
public class QuirofanoController {

    @Autowired
    private ApiBackendQuirofanoService quirofanoService;
    @GetMapping("")
    public ResponseEntity<com.dacs.bff.dto.ApiResponse<List<QuirofanoDTO>>> getAll() {
        try {
            ResponseEntity<List<QuirofanoDTO>> response = quirofanoService.getQuirofanos();
            return com.dacs.bff.util.ApiResponseBuilder.ok(response.getBody());
        } catch (Exception e) {
            return com.dacs.bff.util.ApiResponseBuilder.serverError("Error al obtener quirofanos: " + e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<com.dacs.bff.dto.ApiResponse<QuirofanoDTO>> create (@RequestBody QuirofanoDTO quirofanoDto) {
        try {
            ResponseEntity<QuirofanoDTO> response = quirofanoService.saveQuirofano(quirofanoDto);
            return com.dacs.bff.util.ApiResponseBuilder.created(response.getBody(), "Quirofano creado exitosamente");
        } catch (Exception e) {
            return com.dacs.bff.util.ApiResponseBuilder.serverError("Error al crear quirofano: " + e.getMessage());
        }
    }
}

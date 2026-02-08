package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.api.client.ApiBackendTipoIntervenciones;
import com.dacs.bff.dto.TipoIntervencionDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@PreAuthorize("hasRole('admin') or hasRole('personal_medico')")
@RequestMapping("/tipos-intervenciones")
public class TipoIntervencionController {

    @Autowired
    private ApiBackendTipoIntervenciones tipoIntervencionService;

    @GetMapping("")
    public ResponseEntity<ResponseEntity<List<TipoIntervencionDto>>> getMethodName() {
        try {
            return ResponseEntity.ok(tipoIntervencionService.getTipoIntervenciones());
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    
}

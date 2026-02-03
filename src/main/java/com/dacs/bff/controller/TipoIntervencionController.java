package com.dacs.bff.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dacs.bff.api.client.ApiBackendTipoIntervenciones;
import com.dacs.bff.dto.TipoIntervencionDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/tipos-intervenciones")
public class TipoIntervencionController {

    @Autowired
    private ApiBackendTipoIntervenciones tipoIntervencionService;

    @GetMapping("")
    public ResponseEntity<List<TipoIntervencionDto>> getMethodName() {
        return tipoIntervencionService.getTipoIntervenciones();
    }
    
}

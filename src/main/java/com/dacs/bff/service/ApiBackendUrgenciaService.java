package com.dacs.bff.service;

import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.UrgenciaDTO;

import org.springframework.http.ResponseEntity;

public interface ApiBackendUrgenciaService {

    PaginacionDto.Response<UrgenciaDTO.FrontResponse> getUrgencias(Integer pagina, Integer tamano, String fechaInicio,
            String fechaFin, String estado, String search, String sort, String order);

    ResponseEntity<UrgenciaDTO.FrontResponse> createUrgencia(UrgenciaDTO.FrontRequest urgencia) throws Exception;

    ResponseEntity<UrgenciaDTO.FrontResponse> updateUrgencia(String id, UrgenciaDTO.FrontRequest urgencia) throws Exception;

    ResponseEntity<Void> deleteUrgencia(Long id) throws Exception;
}
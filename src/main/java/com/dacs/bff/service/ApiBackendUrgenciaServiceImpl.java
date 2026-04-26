package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendUrgenciasClient;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.UrgenciaDTO;
import com.dacs.bff.util.PaginatedResponseUtil;
import com.dacs.bff.util.UrgenciaMapper;

@Service
public class ApiBackendUrgenciaServiceImpl implements ApiBackendUrgenciaService {

    @Autowired
    private ApiBackendUrgenciasClient apiBackendUrgenciasClient;

    @Autowired
    private UrgenciaMapper urgenciaMapper;

    @Override
    public PaginacionDto.Response<UrgenciaDTO.FrontResponse> getUrgencias(Integer pagina, Integer tamano, String fechaInicio,
            String fechaFin, String estado, String search, String sort, String order) {
        PaginacionDto.Response<UrgenciaDTO.BackResponse> backResp = apiBackendUrgenciasClient.getUrgencias(pagina, tamano, fechaInicio, fechaFin, estado, search, sort, order);
        List<UrgenciaDTO.FrontResponse> frontList = backResp.getContenido().stream()
                .map(urgenciaMapper::toFrontResponse)
                .toList();
        return PaginatedResponseUtil.build(backResp, frontList);
    }

    @Override
    public ResponseEntity<UrgenciaDTO.FrontResponse> createUrgencia(UrgenciaDTO.FrontRequest urgencia) throws Exception {
        ResponseEntity<UrgenciaDTO.BackResponse> backResp = apiBackendUrgenciasClient.create(urgencia);
        return ResponseEntity.status(backResp.getStatusCode()).body(urgenciaMapper.toFrontResponse(backResp.getBody()));
    }

    @Override
    public ResponseEntity<UrgenciaDTO.FrontResponse> updateUrgencia(String id, UrgenciaDTO.FrontRequest urgencia)
            throws Exception {
        ResponseEntity<UrgenciaDTO.BackResponse> backResp = apiBackendUrgenciasClient.update(id, urgencia);
        return ResponseEntity.status(backResp.getStatusCode()).body(urgenciaMapper.toFrontResponse(backResp.getBody()));
    }

    @Override
    public ResponseEntity<Void> deleteUrgencia(Long id) throws Exception {
        return apiBackendUrgenciasClient.delete(id);
    }
}
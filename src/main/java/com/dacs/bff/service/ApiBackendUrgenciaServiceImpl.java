package com.dacs.bff.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendUrgenciasClient;
import com.dacs.bff.dto.MiembroEquipoDTO;
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

    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public ResponseEntity<UrgenciaDTO.FrontResponse> inicializarUrgencia(Long id) {
        ResponseEntity<UrgenciaDTO.BackResponse> backResp = apiBackendUrgenciasClient.inicializarUrgencia(id);
        return ResponseEntity.status(backResp.getStatusCode()).body(urgenciaMapper.toFrontResponse(backResp.getBody()));
    }

    @Override
    public ResponseEntity<UrgenciaDTO.FrontResponse> finalizarUrgencia(Long id) {
        ResponseEntity<UrgenciaDTO.BackResponse> backResp = apiBackendUrgenciasClient.finalizarUrgencia(id);
        return ResponseEntity.status(backResp.getStatusCode()).body(urgenciaMapper.toFrontResponse(backResp.getBody()));
    }

    @Override
    public ResponseEntity<List<MiembroEquipoDTO.Response>> getEquipoMedico(Long id) {
        ResponseEntity<List<MiembroEquipoDTO.BackResponse>> response = apiBackendUrgenciasClient.getEquipoMedico(id);
        return ResponseEntity.status(response.getStatusCode())
                .body(response.getBody().stream()
                        .map(back -> modelMapper.map(back, MiembroEquipoDTO.Response.class))
                        .toList());
    }

    @Override
    public ResponseEntity<List<MiembroEquipoDTO.Response>> saveEquipoMedico(List<MiembroEquipoDTO.Create> miembros,
            Long id) {
        ResponseEntity<List<MiembroEquipoDTO.BackResponse>> response = apiBackendUrgenciasClient.saveEquipoMedico(id,
                miembros);
        return ResponseEntity.status(response.getStatusCode())
                .body(response.getBody().stream()
                        .map(back -> modelMapper.map(back, MiembroEquipoDTO.Response.class))
                        .toList());
    }

    @Override
    public ResponseEntity<List<com.dacs.bff.dto.IntervencionDto>> getIntervencionesByUrgenciaId(Long urgenciaId) {
        ResponseEntity<List<com.dacs.bff.dto.IntervencionDto>> response = apiBackendUrgenciasClient.getIntervencionesByUrgenciaId(urgenciaId);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @Override
    public ResponseEntity<com.dacs.bff.dto.IntervencionDto> createIntervencionForUrgencia(Long urgenciaId, com.dacs.bff.dto.IntervencionDto intervencion) {
        ResponseEntity<com.dacs.bff.dto.IntervencionDto> response = apiBackendUrgenciasClient.createIntervencionForUrgencia(urgenciaId, intervencion);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @Override
    public ResponseEntity<com.dacs.bff.dto.IntervencionDto> updateIntervencionForUrgencia(Long urgenciaId, Long intervencionId, com.dacs.bff.dto.IntervencionDto intervencion) {
        ResponseEntity<com.dacs.bff.dto.IntervencionDto> response = apiBackendUrgenciasClient.updateIntervencionForUrgencia(urgenciaId, intervencionId, intervencion);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    @Override
    public ResponseEntity<Void> deleteIntervencionForUrgencia(Long urgenciaId, Long intervencionId) {
        return apiBackendUrgenciasClient.deleteIntervencionForUrgencia(urgenciaId, intervencionId);
    }
}
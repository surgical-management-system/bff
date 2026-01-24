package com.dacs.bff.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendPacientesClient;
import com.dacs.bff.api.client.ApiConectorClient;
import com.dacs.bff.dto.PacienteDto;
import com.dacs.bff.dto.PacienteExternoDto;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.util.PacienteMapper;

@Service
public class ApiBackendPacienteServiceImpl implements ApiBackendPacienteService {

    @Autowired
    private ApiBackendPacientesClient apiBackendPacienteClient;

    @Autowired
    private ApiConectorClient apiConectorClient;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PacienteDto.FrontResponse savePaciente(PacienteDto.FrontResponse paciente) throws Exception {

        PacienteDto.BackResponse backResponse = apiBackendPacienteClient
                .save(modelMapper.map(paciente, PacienteDto.BackResponse.class));
        return modelMapper.map(backResponse, PacienteDto.FrontResponse.class);
    }

    @Override
    public PacienteDto.FrontResponse updatePaciente(PacienteDto.BackResponse paciente) throws Exception {
        return modelMapper.map(paciente, PacienteDto.FrontResponse.class);
    }

    @Override
    public ResponseEntity<Void> deletePaciente(Long id) throws Exception {
        return apiBackendPacienteClient.delete(id);
    }


    @Override
    public ResponseEntity<List<PacienteDto.FrontResponse>> getPacientesHospital(Integer cantidad) {
        PacienteExternoDto response = apiConectorClient.getPacientesHospital(cantidad);
        List<PacienteDto.FrontResponse> lista = response.getResults()
                .stream()
                .map(PacienteMapper::fromApiHospitalResponse)
                .toList();

        return ResponseEntity.ok(lista);
    }

        @Override
        public PaginacionDto.Response<PacienteDto.FrontResponse> getPacientesByPage(int page, int size, String search) {
        PaginacionDto.Response<PacienteDto.BackResponse> backendResponse = apiBackendPacienteClient.getPacientes(page, size, search);
        List<PacienteDto.BackResponse> content = backendResponse.getContenido() != null ? backendResponse.getContenido() : java.util.Collections.emptyList();
        List<PacienteDto.FrontResponse> mappedContent = content.stream()
            .map(p -> modelMapper.map(p, PacienteDto.FrontResponse.class))
            .toList();
        return com.dacs.bff.util.PaginatedResponseUtil.build(backendResponse, mappedContent);
        }

    @Override
    public PaginacionDto.Response<PacienteDto.FrontResponseLite> getPacientesLite(int page, int size, String search) {
        PaginacionDto.Response<PacienteDto.FrontResponse> paginatedData = getPacientesByPage(page, size, search);
        List<PacienteDto.FrontResponse> content = paginatedData.getContenido() != null ? paginatedData.getContenido() : java.util.Collections.emptyList();
        List<PacienteDto.FrontResponseLite> mappedContent = content.stream()
            .map(p -> modelMapper.map(p, PacienteDto.FrontResponseLite.class))
            .toList();
        return com.dacs.bff.util.PaginatedResponseUtil.build(paginatedData, mappedContent);
    }
}

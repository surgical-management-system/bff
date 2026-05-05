package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendPersonalClient;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.PersonalDto;
import com.dacs.bff.enums.PersonalRole;
import com.dacs.bff.util.ApiResponseBuilder;
import com.dacs.bff.dto.ApiResponse;

@Service
public class ApiBackendPersonalServiceImpl implements ApiBackendPersonalService {

    @Autowired
    private ApiBackendPersonalClient apiBackendPersonalClient;

    @Override
    public PaginacionDto.Response<PersonalDto.BackResponse> getPersonal(Integer page, Integer size, String param) throws Exception {
        return apiBackendPersonalClient.getPersonal(page, size, param);
    }

    @Override
    public ResponseEntity<PersonalDto.BackResponse> create(PersonalDto.Create personalRequestDto) {
        // Validate role is either admin or personal_medico
        if (!PersonalRole.isValid(personalRequestDto.getRol())) {
            throw new IllegalArgumentException(
                "Invalid role. Allowed roles are: " + 
                String.join(", ", PersonalRole.ADMIN.getValue(), PersonalRole.PERSONAL_MEDICO.getValue())
            );
        }
        return apiBackendPersonalClient.create(personalRequestDto);
    }

    @Override
    public ResponseEntity<PersonalDto.BackResponse> update(Long id, PersonalDto.Update personalRequestDto) {
        // Validate role is either admin or personal_medico
        if (!PersonalRole.isValid(personalRequestDto.getRol())) {
            throw new IllegalArgumentException(
                "Invalid role. Allowed roles are: " + 
                String.join(", ", PersonalRole.ADMIN.getValue(), PersonalRole.PERSONAL_MEDICO.getValue())
            );
        }
        return apiBackendPersonalClient.update(id, personalRequestDto);
    }

    @Override
    public ResponseEntity<Void> delete(Long id) throws Exception {
        return apiBackendPersonalClient.delete(id);

    }

    @Override
    public PaginacionDto.Response<PersonalDto.FrontResponseLite> getPersonalLite(Integer page, Integer size, String param) {
        PaginacionDto.Response<PersonalDto.BackResponse> backResp = apiBackendPersonalClient.getPersonal(page, size, param);
        List<PersonalDto.FrontResponseLite> mappedContent = backResp.getContenido().stream()
            .map(back -> {
                PersonalDto.FrontResponseLite front = new PersonalDto.FrontResponseLite();
                front.setId(back.getId());
                front.setDni(back.getDni());
                front.setLegajo(back.getLegajo());
                // Combine nombre and apellido into a single field for the front-end
                String fullName = (back.getNombre() != null ? back.getNombre() : "")
                    + (back.getApellido() != null && !back.getApellido().isBlank() ? " " + back.getApellido() : "");
                front.setNombre(fullName.trim());
                // Use especialidad as the value for rol in the lite response
                front.setRol(back.getEspecialidad());
                // omit separate apellido in the lite response (front.nombre contains full name)
                front.setApellido(null);
                return front;
            })
            .toList();
        return com.dacs.bff.util.PaginatedResponseUtil.build(backResp, mappedContent);
    }
}
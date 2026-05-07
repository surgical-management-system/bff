package com.dacs.bff.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.dacs.bff.api.client.ApiBackendPersonalClient;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.PersonalDto;
import com.dacs.bff.enums.PersonalRole;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ApiBackendPersonalServiceImpl implements ApiBackendPersonalService {

    @Autowired
    private ApiBackendPersonalClient apiBackendPersonalClient;

    @Value("${feign.client.config.apiBackendPersonalClient.url}")
    private String apiBackendPersonalBaseUrl;

    private String personalBaseUrl() {
        return apiBackendPersonalBaseUrl.endsWith("/")
            ? apiBackendPersonalBaseUrl.substring(0, apiBackendPersonalBaseUrl.length() - 1)
            : apiBackendPersonalBaseUrl;
    }

    private String personalUrl(String path, Integer page, Integer size, String search, String role) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(personalBaseUrl())
            .path(path);

        if (page != null) {
            builder.queryParam("page", page);
        }
        if (size != null) {
            builder.queryParam("size", size);
        }
        if (search != null && !search.isBlank()) {
            builder.queryParam("search", search);
        }
        if (role != null && !role.isBlank()) {
            builder.queryParam("role", role);
        }

        return builder.toUriString();
    }

    @Override
    public PaginacionDto.Response<PersonalDto.BackResponse> getPersonal(Integer page, Integer size, String param, String role) throws Exception {
        String targetUrl = personalUrl("/personal", page, size, param, role);
        log.debug("Calling backend at: {}", targetUrl);

        try {
            if (role == null || role.isBlank()) {
                return apiBackendPersonalClient.getPersonal(page, size, param);
            }
            return apiBackendPersonalClient.getPersonal(page, size, param, role);
        } catch (Exception ex) {
            log.error("Error calling backend at: {}", targetUrl, ex);
            throw ex;
        }
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

        String targetUrl = personalUrl("/personal", null, null, null, null);
        log.debug("Calling backend at: {}", targetUrl);

        try {
            return apiBackendPersonalClient.create(personalRequestDto);
        } catch (Exception ex) {
            log.error("Error calling backend at: {}", targetUrl, ex);
            throw ex;
        }
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

        String targetUrl = personalUrl("/personal/" + id, null, null, null, null);
        log.debug("Calling backend at: {}", targetUrl);

        try {
            return apiBackendPersonalClient.update(id, personalRequestDto);
        } catch (Exception ex) {
            log.error("Error calling backend at: {}", targetUrl, ex);
            throw ex;
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long id) throws Exception {
        String targetUrl = personalUrl("/personal/" + id, null, null, null, null);
        log.debug("Calling backend at: {}", targetUrl);

        try {
            return apiBackendPersonalClient.delete(id);
        } catch (Exception ex) {
            log.error("Error calling backend at: {}", targetUrl, ex);
            throw ex;
        }

    }

    @Override
    public PaginacionDto.Response<PersonalDto.FrontResponseLite> getPersonalLite(Integer page, Integer size, String param, String role) {
        PaginacionDto.Response<PersonalDto.BackResponse> backResp;
        String targetUrl = personalUrl("/personal", page, size, param, role);
        log.debug("Calling backend at: {}", targetUrl);

        try {
            if (role == null || role.isBlank()) {
                backResp = apiBackendPersonalClient.getPersonal(page, size, param);
            } else {
                backResp = apiBackendPersonalClient.getPersonal(page, size, param, role);
            }
        } catch (Exception ex) {
            log.error("Error calling backend at: {}", targetUrl, ex);
            throw ex;
        }
        List<PersonalDto.BackResponse> backendContent = backResp.getContenido() != null ? backResp.getContenido() : java.util.Collections.emptyList();

        // Filter only personnel with estado == "alta" (case-insensitive) because resumen is used for selection
        List<PersonalDto.BackResponse> activeOnly = backendContent.stream()
            .filter(b -> b.getEstado() != null && b.getEstado().equalsIgnoreCase("alta"))
            .toList();

        List<PersonalDto.FrontResponseLite> mappedContent = activeOnly.stream()
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

        // Rebuild pagination response based on filtered content
        com.dacs.bff.dto.PaginacionDto.Response<PersonalDto.FrontResponseLite> response = new com.dacs.bff.dto.PaginacionDto.Response<>();
        response.setContenido(mappedContent);
        int totalElements = activeOnly.size();
        response.setTotalElementos(totalElements);
        int totalPages = size != null && size > 0 ? (int) Math.ceil((double) totalElements / size) : 0;
        response.setTotalPaginas(totalPages);
        response.setPagina(page != null ? page : 0);
        response.setTamaño(size != null ? size : 0);
        return response;
    }
}
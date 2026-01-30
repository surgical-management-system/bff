package com.dacs.bff.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiConectorClient;
import com.dacs.bff.dto.KeycloakUserDto;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.ApiResponse;

@Service
public class ApiConectorServiceImpl implements ApiConectorService {

    @Autowired
    private ApiConectorClient apiConectorClient;

    @Override
    public ApiResponse<KeycloakUserDto> getUsuarioById(String id) {
        return apiConectorClient.getUsuarioById(id);
    }

    @Override
    public PaginacionDto.Response<KeycloakUserDto> getUsuarios(int page, int size, String search) {
        return apiConectorClient.getUsuarios(page, size, search);
    }

    @Override
    public ApiResponse<KeycloakUserDto> toggleUsuarioStatus(String id, boolean enabled) {
        return apiConectorClient.toggleUsuarioStatus(id, enabled);
    }

    @Override
    public ApiResponse<KeycloakUserDto> createUsuario(KeycloakUserDto.Create user) {
     
        return apiConectorClient.createUser(user); // Reemplazar con la lógica real
    }
}

package com.dacs.bff.service;

import com.dacs.bff.dto.KeycloakUserDto;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.PaginacionDto;
import org.springframework.stereotype.Service;

@Service
public class KeycloakUserService {
    public PaginacionDto<KeycloakUserDto> getUsuarios(int page, int size, String search) {
        // TODO: Implementar integración con Keycloak para obtener usuarios paginados y búsqueda
        return new PaginacionDto<>();
    }

    public ApiResponse<KeycloakUserDto> getUsuarioById(String id) {
        // TODO: Implementar integración con Keycloak para obtener usuario por ID
        return new ApiResponse<>();
    }

    public ApiResponse<KeycloakUserDto> toggleUsuarioStatus(String id, boolean enabled) {
        // TODO: Implementar integración con Keycloak para activar/desactivar usuario
        return new ApiResponse<>();
    }
}

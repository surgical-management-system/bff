package com.dacs.bff.service;


import com.dacs.bff.dto.KeycloakUserDto;
import com.dacs.bff.dto.KeycloakUserDto.Create;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.ApiResponse;

public interface ApiConectorService {
    ApiResponse<KeycloakUserDto> getUsuarioById(String id);
    PaginacionDto.Response<KeycloakUserDto> getUsuarios(int page, int size, String search);
    ApiResponse<KeycloakUserDto> toggleUsuarioStatus(String id, boolean enabled);
    ApiResponse<KeycloakUserDto> createUsuario(Create user);
}
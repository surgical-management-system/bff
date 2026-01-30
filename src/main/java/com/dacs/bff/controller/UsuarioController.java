package com.dacs.bff.controller;

import com.dacs.bff.dto.KeycloakUserDto;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.service.ApiConectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private ApiConectorService keycloakUserService;

    @GetMapping("")
    public ResponseEntity<PaginacionDto.Response<KeycloakUserDto>> getUsuarios(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "16") int size,
            @RequestParam(required = false) String search) {
        return ResponseEntity.ok(keycloakUserService.getUsuarios(page, size, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KeycloakUserDto>> getUsuarioById(@PathVariable String id) {
        return ResponseEntity.ok(keycloakUserService.getUsuarioById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<KeycloakUserDto>> toggleUsuarioStatus(
            @PathVariable String id,
            @RequestBody ToggleStatusRequest request) {
        return ResponseEntity.ok(keycloakUserService.toggleUsuarioStatus(id, request.isEnabled()));
    }

    // DTO interno para el request de status
    public static class ToggleStatusRequest {
        private boolean enabled;
        public boolean isEnabled() { return enabled; }
        public void setEnabled(boolean enabled) { this.enabled = enabled; }
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<KeycloakUserDto>> createUsuario(@RequestBody KeycloakUserDto.Create user) {
        
        return ResponseEntity.ok(keycloakUserService.createUsuario(user));
    }
    
}

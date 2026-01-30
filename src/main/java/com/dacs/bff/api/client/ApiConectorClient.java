package com.dacs.bff.api.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.ApiResponse;
import com.dacs.bff.dto.KeycloakUserDto;
import com.dacs.bff.dto.KeycloakUserDto.Create;
import com.dacs.bff.dto.PacienteExternoDto;
import com.dacs.bff.dto.PaginacionDto;

@FeignClient(name = "apiConectorClient", url = "${feign.client.config.apiconectorclient.url}", configuration = FeignConfig.class)
public interface ApiConectorClient {

	@GetMapping("/api/external/paciente")
	PacienteExternoDto getPacientesHospital(@RequestParam("cantidad") int cantidad,
			@RequestParam("nacionalidad") String nacionalidad);

	@GetMapping("api/external/users/{id}")
	ApiResponse<com.dacs.bff.dto.KeycloakUserDto> getUsuarioById(@PathVariable("id") String id);

	@GetMapping("api/external/users")
	PaginacionDto.Response<com.dacs.bff.dto.KeycloakUserDto> getUsuarios(
			@RequestParam("page") int page,
			@RequestParam("size") int size,
			@RequestParam(value = "search", required = false) String search);

	@GetMapping("api/external/users/{id}/status")
	ApiResponse<KeycloakUserDto> toggleUsuarioStatus(
			@PathVariable("id") String id,
			@RequestParam("enabled") boolean enabled);

	@PostMapping("/api/external/users")
	 ApiResponse<KeycloakUserDto> createUser(@RequestBody KeycloakUserDto.Create user);
}

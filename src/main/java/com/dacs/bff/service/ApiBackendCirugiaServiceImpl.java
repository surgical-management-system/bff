package com.dacs.bff.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendCirugiasClient;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.ServicioDto;
import com.dacs.bff.util.CirugiaMapper;

@Service
public class ApiBackendCirugiaServiceImpl implements ApiBackendCirugiaService {

	@Autowired
	private ApiBackendCirugiasClient apiBackendCirugiaClient;

	@Autowired
	private CirugiaMapper cirugiaMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public PaginacionDto.Response<CirugiaDTO.FrontResponse> getCirugias(Integer pagina, Integer tamaño, String fechaInicio, String fechaFin) {
		PaginacionDto.Response<CirugiaDTO.BackResponse> backResp = apiBackendCirugiaClient.getCirugias(pagina, tamaño, fechaInicio, fechaFin);
		List<CirugiaDTO.FrontResponse> frontList = backResp.getContenido().stream()
				.map(item -> cirugiaMapper.toFrontResponse(item))
				.toList();
		return com.dacs.bff.util.PaginatedResponseUtil.build(backResp, frontList);
	}

	@Override
	public ResponseEntity<CirugiaDTO.FrontResponse> createCirugia(CirugiaDTO.FrontRequest cirugia) throws Exception {
		
		ResponseEntity<CirugiaDTO.BackResponse> backResp = apiBackendCirugiaClient.create(cirugia);

		return ResponseEntity.status(backResp.getStatusCode()).body(cirugiaMapper.toFrontResponse(backResp.getBody()));
	}

	@Override
	public ResponseEntity<CirugiaDTO.FrontResponse> updateCirugia(String id, CirugiaDTO.FrontRequest cirugia)
			throws Exception {
		ResponseEntity<CirugiaDTO.BackResponse> backResp = apiBackendCirugiaClient.update(id, cirugia);
		return ResponseEntity.status(backResp.getStatusCode()).body(cirugiaMapper.toFrontResponse(backResp.getBody()));
	}

	@Override
	public ResponseEntity<Void> deleteCirugia(Long id) throws Exception {

		return apiBackendCirugiaClient.delete(id);

	}

	@Override
	public ResponseEntity<List<MiembroEquipoDTO.Response>> getEquipoMedico(Long id) {
		ResponseEntity<List<MiembroEquipoDTO.BackResponse>> response = apiBackendCirugiaClient.getEquipoMedico(id);
		return ResponseEntity.status(response.getStatusCode())
				.body(response.getBody().stream()
						.map(back -> modelMapper.map(back, MiembroEquipoDTO.Response.class))
						.toList());
	}

	@Override
	public ResponseEntity<List<MiembroEquipoDTO.Response>> saveEquipoMedico(List<MiembroEquipoDTO.Create> miembros,
			Long id) {
		ResponseEntity<List<MiembroEquipoDTO.BackResponse>> response = apiBackendCirugiaClient.saveEquipoMedico(id,
				miembros);
		return ResponseEntity.status(response.getStatusCode())
				.body(response.getBody().stream()
						.map(back -> modelMapper.map(back, MiembroEquipoDTO.Response.class))
						.toList());
	}


	@Override
	public ResponseEntity<List<ServicioDto>> getServicios(int tamaño, int pagina) {

		return apiBackendCirugiaClient.getServicios(tamaño, pagina);
	}
}

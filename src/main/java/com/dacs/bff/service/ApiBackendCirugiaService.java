package com.dacs.bff.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.CirugiaDTO.FrontResponse;
import com.dacs.bff.dto.IntervencionDto;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.ServicioDto;

public interface ApiBackendCirugiaService {

            public PaginacionDto.Response<CirugiaDTO.FrontResponse> getCirugias(Integer pagina, Integer tamaño, String fechaInicio,
                    String fechaFin, String estado, String search, String sort, String order);

    public ResponseEntity<CirugiaDTO.FrontResponse> createCirugia(CirugiaDTO.FrontRequest cirugia) throws Exception;

    public ResponseEntity<Void> deleteCirugia(Long id) throws Exception;

    public ResponseEntity<List<MiembroEquipoDTO.Response>> getEquipoMedico(Long id);

    public ResponseEntity<List<MiembroEquipoDTO.Response>> saveEquipoMedico(List<MiembroEquipoDTO.Create> miembros, Long id);

    public ResponseEntity<List<ServicioDto>> getServicios(int tamaño, int pagina);

    public ResponseEntity<CirugiaDTO.FrontResponse> updateCirugia(String id, CirugiaDTO.FrontRequest cirugia)
            throws Exception;

    public ResponseEntity<FrontResponse> inicializarCirugia(Long id);

    public ResponseEntity<List<IntervencionDto>> getIntervencionesByCirugiaId(Long cirugiaId);

    public ResponseEntity<IntervencionDto> createIntervencion(Long cirugiaId, IntervencionDto intervencion);

    public ResponseEntity<IntervencionDto> updateIntervencion(Long cirugiaId, Long intervencionId, IntervencionDto intervencion);

    public ResponseEntity<Void> deleteIntervencion(Long cirugiaId, Long intervencionId);

    public ResponseEntity<FrontResponse> finalizarCirugia(Long id);

}

package com.dacs.bff.service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.CirugiaDTO;
import com.dacs.bff.dto.MiembroEquipoDTO;
import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.ServicioDto;

public interface ApiBackendCirugiaService {

    public PaginacionDto.Response<CirugiaDTO.FrontResponse> getCirugias(Integer pagina, Integer tamaño, String fechaInicio, String fechaFin);

    public ResponseEntity<CirugiaDTO.FrontResponse> createCirugia(CirugiaDTO.FrontRequest cirugia) throws Exception;

    public ResponseEntity<Void> deleteCirugia(Long id) throws Exception;

    public ResponseEntity<List<MiembroEquipoDTO.Response>> getEquipoMedico(Long id);

    public ResponseEntity<List<MiembroEquipoDTO.Response>> saveEquipoMedico(List<MiembroEquipoDTO.Create> miembros, Long id);

    public ResponseEntity<List<ServicioDto>> getServicios(int tamaño, int pagina);

    public ResponseEntity<CirugiaDTO.FrontResponse> updateCirugia(String id, CirugiaDTO.FrontRequest cirugia)
            throws Exception;

}

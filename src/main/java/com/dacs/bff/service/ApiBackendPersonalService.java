package com.dacs.bff.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.dacs.bff.dto.PaginacionDto;
import com.dacs.bff.dto.PersonalDto;

public interface ApiBackendPersonalService {     //Cambiar todos a frontresponse
    PaginacionDto.Response<PersonalDto.BackResponse> getPersonal(Integer page, Integer size, String param) throws Exception;

    ResponseEntity<PersonalDto.BackResponse> create(PersonalDto.Create personalRequestDto);

    ResponseEntity<PersonalDto.BackResponse> update(Long id,PersonalDto.Update personalRequestDto);

    ResponseEntity<Void> delete(Long id) throws Exception;

    PaginacionDto.Response<PersonalDto.FrontResponseLite> getPersonalLite(Integer page, Integer size, String param);
}

package com.dacs.bff.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dacs.bff.api.client.ApiBackendQuirofanoClient;
import com.dacs.bff.dto.QuirofanoDTO;


@Service
public class ApiBackendQuirofanoServiceImpl implements ApiBackendQuirofanoService {

    @Autowired
    private ApiBackendQuirofanoClient apiBackendQuirofanoClient;

    @Override
    public ResponseEntity<QuirofanoDTO> saveQuirofano(QuirofanoDTO quirofano) throws Exception {
        return apiBackendQuirofanoClient.save(quirofano);
    }

    @Override
    public ResponseEntity<QuirofanoDTO> updateQuirofano(QuirofanoDTO quirofano) throws Exception {
        return apiBackendQuirofanoClient.update(quirofano);
    }
    
    @Override
    public ResponseEntity<QuirofanoDTO> deleteQuirofano(Long id) throws Exception {
        return apiBackendQuirofanoClient.delete(id);
    }

    @Override
    public ResponseEntity<List<QuirofanoDTO>> getQuirofanos() {
        return apiBackendQuirofanoClient.quirofanos();
    }

}

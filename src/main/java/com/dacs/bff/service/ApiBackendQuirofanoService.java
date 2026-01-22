package com.dacs.bff.service;

import com.dacs.bff.dto.QuirofanoDTO;

import java.util.List;

import org.springframework.http.ResponseEntity;

public interface ApiBackendQuirofanoService {
    public ResponseEntity<List<QuirofanoDTO>> getQuirofanos();
    
    public ResponseEntity<QuirofanoDTO> saveQuirofano(QuirofanoDTO quirofano) throws Exception;

    public ResponseEntity<QuirofanoDTO> updateQuirofano(QuirofanoDTO quirofano) throws Exception;
    
    public ResponseEntity<QuirofanoDTO> deleteQuirofano(Long id) throws Exception;
}

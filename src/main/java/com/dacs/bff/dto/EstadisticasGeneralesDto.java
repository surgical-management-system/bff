package com.dacs.bff.dto;

import java.util.List;


import lombok.Data;

@Data
public class EstadisticasGeneralesDto {
    private Long totalPacientes;
    private Long cirugiasPendientes;
    private Long cirugiasHoy;
    private Long cirugiasEstaSemana;
    private List<QuirofanoDTO> quirofanosDisponibles;
    private List<QuirofanoDTO> quirofanosEnUso;

}
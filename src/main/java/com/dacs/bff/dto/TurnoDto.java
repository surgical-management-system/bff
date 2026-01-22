package com.dacs.bff.dto;

import lombok.Data;

@Data
public class TurnoDto {

    private Long id;

    private String fechaHoraInicio;

    private Long cirugiaId;

    private Long quirofanoId;

    private String estado;
}

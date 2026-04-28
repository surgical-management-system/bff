package com.dacs.bff.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UrgenciaDTO {

    @Data
    public static class BackResponse {
        private Long id;
        private Integer nivelUrgencia;
        private LocalDateTime fechaHoraInicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private PacienteDto.BackResponse paciente;
        private QuirofanoDTO quirofano;
        private ServicioDto servicio;
    }

    @Data
    public static class FrontResponse {
        private Long id;
        private LocalDateTime fechaHoraInicio;
        private Long pacienteId;
        private String pacienteNombre;
        private PacienteDto.BackResponse paciente;
        private String nombrePaciente;
        private String dni;
        private String servicioNombre;
        private ServicioDto servicio;
        private String nombreServicio;
        private Long servicioId;
        private Integer nivelUrgencia;
        private String fechaInicio;
        private String horaInicio;
        private String horaFin;
        private String estado;
        private String anestesia;
        private String tipo;
        private String quirofanoNombre;
        private QuirofanoDTO quirofano;
        private String nombreQuirofano;
        private Long quirofanoId;
    }

    @Data
    public static class FrontRequest {
        private Long pacienteId;
        private Long servicioId;
        private Integer nivelUrgencia;
        private LocalDateTime fechaHoraInicio;
        private String estado;
        private String anestesia;
        private String tipo;
        private Long quirofanoId;
    }
}
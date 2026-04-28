package com.dacs.bff.util;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.dacs.bff.dto.UrgenciaDTO;

@Component
public class UrgenciaMapper {
    private static final Logger log = LoggerFactory.getLogger(UrgenciaMapper.class);

    public UrgenciaDTO.FrontResponse toFrontResponse(UrgenciaDTO.BackResponse backResp) {
        if (backResp == null) {
            return null;
        }

        UrgenciaDTO.FrontResponse front = new UrgenciaDTO.FrontResponse();
        front.setId(backResp.getId());
        front.setFechaHoraInicio(backResp.getFechaHoraInicio());
        front.setNivelUrgencia(backResp.getNivelUrgencia());
        front.setEstado(backResp.getEstado());
        front.setAnestesia(backResp.getAnestesia());
        front.setTipo(backResp.getTipo());
        front.setServicio(backResp.getServicio());
        front.setPaciente(backResp.getPaciente());
        front.setQuirofano(backResp.getQuirofano());

        if (backResp.getServicio() != null) {
            front.setServicioId(backResp.getServicio().getId());
            if (backResp.getServicio().getNombre() != null) {
                front.setServicioNombre(backResp.getServicio().getNombre());
                front.setNombreServicio(backResp.getServicio().getNombre());
            }
        }

        if (backResp.getPaciente() != null) {
            front.setDni(backResp.getPaciente().getDni());
            front.setPacienteId(backResp.getPaciente().getId());
            String nombrePaciente = backResp.getPaciente().getNombre();
            String apellidoPaciente = backResp.getPaciente().getApellido();
            if (nombrePaciente != null && apellidoPaciente != null) {
                front.setPacienteNombre(nombrePaciente + " " + apellidoPaciente);
                front.setNombrePaciente(nombrePaciente + " " + apellidoPaciente);
            } else if (nombrePaciente != null) {
                front.setPacienteNombre(nombrePaciente);
                front.setNombrePaciente(nombrePaciente);
            } else {
                front.setPacienteNombre(apellidoPaciente);
                front.setNombrePaciente(apellidoPaciente);
            }
        }

        if (backResp.getFechaHoraInicio() != null) {
            String fechaHoraCompleta = backResp.getFechaHoraInicio().toString();
            try {
                java.time.LocalDateTime ldt;
                if (fechaHoraCompleta.endsWith("Z") || fechaHoraCompleta.matches(".*[+-][0-9]{2}:[0-9]{2}$")) {
                    OffsetDateTime odt = OffsetDateTime.parse(fechaHoraCompleta);
                    ldt = odt.toLocalDateTime();
                } else {
                    ldt = java.time.LocalDateTime.parse(fechaHoraCompleta);
                }
                String fecha = ldt.toLocalDate().toString();
                String horaInicio = ldt.toLocalTime().toString().substring(0, 5);
                String horaFin = horaInicio;
                if (backResp.getServicio() != null && backResp.getServicio().getDuracionMinutos() != null) {
                    horaFin = ldt.toLocalTime().plusMinutes(backResp.getServicio().getDuracionMinutos()).toString().substring(0, 5);
                }
                front.setFechaInicio(fecha);
                front.setHoraInicio(horaInicio);
                front.setHoraFin(horaFin);
            } catch (Exception e) {
                log.error("Error parseando fecha_hora_inicio: {}", fechaHoraCompleta, e);
            }
        }

        if (backResp.getQuirofano() != null && backResp.getQuirofano().getNombre() != null) {
            front.setQuirofanoNombre(backResp.getQuirofano().getNombre());
            front.setNombreQuirofano(backResp.getQuirofano().getNombre());
            front.setQuirofanoId(Long.valueOf(backResp.getQuirofano().getId()));
        }

        return front;
    }
}
package com.dacs.bff.util;

import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dacs.bff.dto.CirugiaDTO;


@Component

public class CirugiaMapper {
    private static final Logger log = LoggerFactory.getLogger(CirugiaMapper.class);

    public CirugiaDTO.FrontResponse toFrontResponse(CirugiaDTO.BackResponse backResp) {
        if (backResp == null) {
            return null;
        }
        CirugiaDTO.FrontResponse front = new CirugiaDTO.FrontResponse();

        // copiar campos simples
        front.setId(backResp.getId());
        front.setPrioridad(backResp.getPrioridad());
        front.setEstado(backResp.getEstado());
        front.setAnestesia(backResp.getAnestesia());
        front.setTipo(backResp.getTipo());

        // extraer nombre del servicio
        if (backResp.getServicio() != null) {
            front.setServicioId(backResp.getServicio().getId());
            if (backResp.getServicio().getNombre() != null) {
                front.setServicioNombre(backResp.getServicio().getNombre());
            }
        }

        // extraer nombre del paciente (concatenar nombre + apellido o usar campo
        // nombre)
        if (backResp.getPaciente() != null) {
            front.setDni(backResp.getPaciente().getDni());
            front.setPacienteId(backResp.getPaciente().getId());
            String nombrePaciente = backResp.getPaciente().getNombre();
            String apellidoPaciente = backResp.getPaciente().getApellido();
            if (nombrePaciente != null && apellidoPaciente != null) {
                front.setPacienteNombre(nombrePaciente + " " + apellidoPaciente);
            } else if (nombrePaciente != null) {
                front.setPacienteNombre(nombrePaciente);
            } else {
                front.setPacienteNombre(apellidoPaciente);
            }
        }

        // Extraer fecha y hora
        if (backResp.getFechaHoraInicio() != null) {
            String fechaHoraCompleta = backResp.getFechaHoraInicio().toString();
            try {
                java.time.LocalDateTime ldt;
                // Detectar si tiene zona horaria: termina en 'Z' o contiene un offset tipo +HH:mm o -HH:mm al final
                if (fechaHoraCompleta.endsWith("Z") || fechaHoraCompleta.matches(".*[+-][0-9]{2}:[0-9]{2}$")) {
                    OffsetDateTime odt = OffsetDateTime.parse(fechaHoraCompleta);
                    ldt = odt.toLocalDateTime();
                } else {
                    ldt = java.time.LocalDateTime.parse(fechaHoraCompleta);
                }
                String fecha = ldt.toLocalDate().toString(); // yyyy-MM-dd
                String horaInicio = ldt.toLocalTime().toString().substring(0, 5); // HH:mm
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

        // extraer nombre del quirófano (asume que QuirofanoDto tiene getNombre())
        if (backResp.getQuirofano() != null && backResp.getQuirofano().getNombre() != null) {
            front.setQuirofanoNombre(backResp.getQuirofano().getNombre());
            front.setQuirofanoId(Long.valueOf(backResp.getQuirofano().getId()));
        }

        return front;
    }

}

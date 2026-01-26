package com.dacs.bff.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PaginacionDto<T> {
    private int pagina;
    private int tamaño;


    @Setter
    @Getter
    public static class Turnos extends PaginacionDto<TurnoDto>{
        private String fechaInicio;
        private String fechaFin;
    }


    @Getter
    @Setter
    public static class Response<T> extends PaginacionDto<T> 
    {
        private long totalElementos;
        private int totalPaginas;
        private List<T> contenido;
    }
}



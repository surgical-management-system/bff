package com.dacs.bff.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class PaginacionDto<T> {
    private int page;
    private int size;


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
        private long totalElements;
        private int totalPages;
        private List<T> content;
    }
}



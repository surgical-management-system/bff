package com.dacs.bff.util;

import com.dacs.bff.dto.PaginacionDto;
import java.util.List;

public class PaginatedResponseUtil {
    public static <T, B> PaginacionDto.Response<T> build(PaginacionDto.Response<B> backend, List<T> content) {
        PaginacionDto.Response<T> response = new PaginacionDto.Response<>();
        response.setContenido(content);
        response.setTotalElementos(backend.getTotalElementos());
        response.setTotalPaginas(backend.getTotalPaginas());
        response.setPagina(backend.getPagina());
        response.setTamaño(backend.getTamaño());
        return response;
    }
}

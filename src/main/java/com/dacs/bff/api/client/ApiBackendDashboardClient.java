package com.dacs.bff.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.dacs.bff.config.FeignConfig;
import com.dacs.bff.dto.EstadisticasGeneralesDto;


@FeignClient(name = "apiBackendDashboardClient", url = "${feign.client.config.apiBackendDashboardClient.url}", configuration = FeignConfig.class)

public interface ApiBackendDashboardClient {


    @GetMapping("/dashboard/general")
    ResponseEntity<EstadisticasGeneralesDto> getEstadisticasGenerales();
    

}
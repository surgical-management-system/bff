package com.dacs.bff.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import com.dacs.bff.exeption.BackendApiException;

import feign.Response;
import feign.codec.ErrorDecoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class FeignConfig {

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }

    @Bean
    public ErrorDecoder errorDecoder(ObjectMapper objectMapper) {
        return (methodKey, response) -> {
            HttpStatus status = HttpStatus.resolve(response.status());
            String body = readBody(response);

            if (status != null && body != null && !body.isBlank()) {
                try {
                    JsonNode node = objectMapper.readTree(body);
                    String code = node.path("code").asText("BACKEND_ERROR");
                    String description = node.path("description").asText(body);
                    return new BackendApiException(status, code, description);
                } catch (Exception ignored) {
                    return new BackendApiException(status, "BACKEND_ERROR", body);
                }
            }

            return new BackendApiException(
                    status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR,
                    "BACKEND_ERROR",
                    body != null && !body.isBlank() ? body : "Unexpected backend error");
        };
    }

    private String readBody(Response response) {
        if (response.body() == null) {
            return null;
        }

        try {
            return new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return null;
        }
    }
}

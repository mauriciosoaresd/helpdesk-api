package com.mauriciosoaresd.backend_gestao_os_api.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErroResponseDTO(
        int status,
        String mensagem,
        LocalDateTime timestamp,
        Map<String, String> errosDeValidacao // Mapeia o campo e a mensagem de erro (ex: "email": "Formato inválido")
) {
    public static ErroResponseDTO simples(int status, String mensagem) {
        return new ErroResponseDTO(status, mensagem, LocalDateTime.now(), null);
    }

    public static ErroResponseDTO comErrosValidacao(int status, String mensagem, Map<String, String> erros) {
        return new ErroResponseDTO(status, mensagem, LocalDateTime.now(), erros);
    }
}
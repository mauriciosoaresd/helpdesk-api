package com.mauriciosoaresd.backend_gestao_os_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Captura erros de validação do Bean Validation (@Valid nos DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponseDTO> tratarErrosDeValidacao(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            erros.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErroResponseDTO response = ErroResponseDTO.comErrosValidacao(
                HttpStatus.BAD_REQUEST.value(),
                "Falha na validação dos campos enviados",
                erros
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Captura exceções de regra de negócio (ex: e-mail já cadastrado)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroResponseDTO> tratarRegraDeNegocio(IllegalArgumentException ex) {
        ErroResponseDTO response = ErroResponseDTO.simples(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Captura erros de autenticação e credenciais inválidas
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErroResponseDTO> tratarCredenciaisInvalidas(BadCredentialsException ex) {
        ErroResponseDTO response = ErroResponseDTO.simples(
                HttpStatus.UNAUTHORIZED.value(),
                "E-mail ou senha inválidos"
        );

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    // Captura qualquer outro erro não tratado (Fallback 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroResponseDTO> tratarErroInesperado(Exception ex) {
        ErroResponseDTO response = ErroResponseDTO.simples(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Ocorreu um erro interno no servidor. Tente novamente mais tarde."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
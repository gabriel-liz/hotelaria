package com.hotelaria.hotelaria.api.exceptionhandler;

import com.hotelaria.hotelaria.domain.exception.EntidadeNaoEncontradaException;
import com.hotelaria.hotelaria.domain.exception.NegocioException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocio(NegocioException ex) {
        return ResponseEntity.badRequest().body(Map.of("Motivo", ex.getMessage()));
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("Motivo", ex.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String motivo = "Violação de integridade de dados. O recurso pode já existir ou dados referenciados não existem.";
        if (ex.getCause() != null && ex.getCause().getCause() != null) {
            String rootMessage = ex.getCause().getCause().getMessage();
            if (rootMessage.contains("duplicate key")) {
                motivo = "Erro: Já existe um registro com os dados informados.";
            } else if (rootMessage.contains("violates foreign key")) {
                motivo = "Erro: Não é possível realizar a operação devido a referências de dados inválidas.";
            }
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("Motivo", motivo));
    }
}
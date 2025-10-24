package com.hotelaria.hotelaria.api.exceptionhandler;

import com.hotelaria.hotelaria.domain.exception.NegocioException;
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
}

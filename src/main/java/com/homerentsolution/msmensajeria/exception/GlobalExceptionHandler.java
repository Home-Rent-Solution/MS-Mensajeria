package com.homerentsolution.msmensajeria.exception;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(
            MethodArgumentNotValidException ex) {

        String mensaje = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .orElse("Solicitud invalida");

        ErrorResponse error =
                new ErrorResponse(

                        mensaje,

                        HttpStatus.BAD_REQUEST.value(),

                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> manejarRuntime(
            RuntimeException ex) {

        ErrorResponse error =
                new ErrorResponse(

                        ex.getMessage(),

                        HttpStatus.BAD_REQUEST.value(),

                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(
            Exception ex) {

        ErrorResponse error =
                new ErrorResponse(

                        "Error interno del servidor",

                        HttpStatus.INTERNAL_SERVER_ERROR.value(),

                        LocalDateTime.now()
                );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}

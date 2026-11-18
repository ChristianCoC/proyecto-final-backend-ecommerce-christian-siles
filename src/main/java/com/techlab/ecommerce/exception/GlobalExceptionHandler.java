package com.techlab.ecommerce.exception;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.techlab.ecommerce.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            HttpStatus.NOT_FOUND.value(),
            "No encontrado",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ResponseEntity<ErrorResponseDTO> handleStockException(StockInsuficienteException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(
            HttpStatus.BAD_REQUEST.value(),
            "Stock insuficiente",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String mensajes = ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "));

        ErrorResponseDTO error = new ErrorResponseDTO(
            HttpStatus.BAD_REQUEST.value(),
            "Error de validación",
            mensajes
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    } 
}

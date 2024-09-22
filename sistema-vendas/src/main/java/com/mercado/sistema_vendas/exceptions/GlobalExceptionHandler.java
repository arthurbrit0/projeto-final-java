package com.mercado.sistema_vendas.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Trata erros de validação de argumentos (400 Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Trata ProdutoNotFoundException (404 Not Found)
    @ExceptionHandler(ProdutoNotFoundException.class)
    public ResponseEntity<String> handleProdutoNotFoundException(ProdutoNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // Trata ProdutoEmUsoException (409 Conflict)
    @ExceptionHandler(ProdutoEmUsoException.class)
    public ResponseEntity<String> handleProdutoEmUsoException(ProdutoEmUsoException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Trata violações de integridade de dados, como campos únicos (409 Conflict)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {
        return new ResponseEntity<>("Código já existente. Por favor, escolha outro código.", HttpStatus.CONFLICT);
    }

    // Trata outras exceções genéricas (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return new ResponseEntity<>("Ocorreu um erro interno no servidor.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

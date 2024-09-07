package org.gomgom.parkingplace.Exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/*
작성자: 오지수
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    //@Data
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<ExceptionResponse> exceptionResponses = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            exceptionResponses.add(new ExceptionResponse(error.getDefaultMessage()));
        });
        return new ResponseEntity<>(exceptionResponses, HttpStatus.BAD_REQUEST);
    }

    //@Data
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ExceptionResponse>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<ExceptionResponse> exceptionResponses = new ArrayList<>();
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            exceptionResponses.add(new ExceptionResponse(constraintViolation.getMessage()));
        }
        return new ResponseEntity<>(exceptionResponses, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomExceptions.ValidationException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(CustomExceptions.ValidationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(CustomExceptions.UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(CustomExceptions.UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ExceptionResponse(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ExceptionResponse("예상치 못한 오류가 발생하였습니다."));
    }
}

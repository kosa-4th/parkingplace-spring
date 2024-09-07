package org.gomgom.parkingplace.Exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/*
작성자: 오지수
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 요청 데이터가 올바른 타입이 아닌 경우 처리하는 ExceptionHandler
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> handleNoSuchElementException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse("입력 값이 올바른 형태가 아닙니다."));
    }

    /**
     * 작성자: 양건모
     * 시작 일자: 2024.09.07
     * 설명 : 원하는 데이터가 없는 경우 처리하는 ExceptionHandler
     *  ---------------------
     * 2024.09.07 양건모 | 기능 구현
     * */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ExceptionResponse> handleNoSuchElementException(NoSuchElementException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse("해당 결과를 찾을 수 없습니다."));
    }

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

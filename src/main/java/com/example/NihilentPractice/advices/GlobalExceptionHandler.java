package com.example.NihilentPractice.advices;

import com.example.NihilentPractice.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException exception){
    ApiError apiError = ApiError.builder()
            .status(HttpStatus.NOT_FOUND)
//            .message("Resource not found")
            .message(exception.getMessage())
            .build();
    return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
}

@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInputValidationErrors(MethodArgumentNotValidException exception){
    List<String> errors = exception
            .getBindingResult()
            .getAllErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .collect(Collectors.toList());

    ApiError apiError = ApiError.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message("Input Validation failed")
            .subErrors(errors)
            .build();
            return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
}
}

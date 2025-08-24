package com.company;

import com.company.exception.DeveloperNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//To handle the global exceptions
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<String> handleDevNotFoundException(DeveloperNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

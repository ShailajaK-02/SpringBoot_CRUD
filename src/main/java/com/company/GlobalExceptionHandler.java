package com.company;

import com.company.exception.DeveloperNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//To handle the global exceptions
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handleAll(Exception ex) {
//        ex.printStackTrace(); // optional: log it
//        return ResponseEntity.internalServerError()
//                .body("Something went wrong.");
//    }
    @ExceptionHandler(DeveloperNotFoundException.class)
    public ResponseEntity<String> handleDevNotFoundException(DeveloperNotFoundException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}

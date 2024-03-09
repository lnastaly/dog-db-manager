package com.mpr.projekt.controller;

import com.mpr.projekt.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DogExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DogNotFoundException.class)
    protected ResponseEntity<String> handleNotFound(DogNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(NoDogsFoundException.class)
    protected ResponseEntity<String> handleNoDogsFound(NoDogsFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DogAlreadyExistsException.class)
    protected ResponseEntity<String> handleDogAlreadyExists(DogAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidDogIdException.class)
    protected ResponseEntity<Object> handleInvalidId(InvalidDogIdException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidDogNameException.class)
    protected ResponseEntity<Object> handleInvalidName(InvalidDogNameException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidDogAgeException.class)
    protected ResponseEntity<Object> handleInvalidAge(InvalidDogAgeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

}

package com.mpr.projekt.exception;

public class DogAlreadyExistsException extends RuntimeException {

    public DogAlreadyExistsException(String message) {
        super(message);
    }

}

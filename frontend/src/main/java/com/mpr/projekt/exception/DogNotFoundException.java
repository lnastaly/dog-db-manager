package com.mpr.projekt.exception;

public class DogNotFoundException extends RuntimeException {

    public DogNotFoundException(String message) {
        super(message);
    }

}

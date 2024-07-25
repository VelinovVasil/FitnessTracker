package com.fitnesstracker.fitnesstracker.handler.exceptions;

public class LocationNotFoundException extends RuntimeException {
    public LocationNotFoundException(String message) {
        super(message);
    }
}

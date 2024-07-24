package com.fitnesstracker.fitnesstracker.handler.exceptions;

public class ExerciseNotFoundException extends RuntimeException {

    public ExerciseNotFoundException(String message) {
        super(message);
    }
}

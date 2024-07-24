package com.fitnesstracker.fitnesstracker.handler.exceptions;

public class WorkoutNotFoundException extends RuntimeException {

    public WorkoutNotFoundException(String message) {
        super(message);
    }
}

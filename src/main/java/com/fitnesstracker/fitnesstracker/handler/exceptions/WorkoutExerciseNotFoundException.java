package com.fitnesstracker.fitnesstracker.handler.exceptions;

public class WorkoutExerciseNotFoundException extends RuntimeException {

    public WorkoutExerciseNotFoundException(String message) {
        super(message);
    }
}

package com.fitnesstracker.fitnesstracker.handler.exceptions;

public class RecipeNotFoundException extends RuntimeException {

    public RecipeNotFoundException(String message) {
        super(message);
    }
}

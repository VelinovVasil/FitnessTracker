package com.fitnesstracker.fitnesstracker.handler;


import com.fitnesstracker.fitnesstracker.handler.exceptions.ExerciseNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.WorkoutNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalErrorHandler {

    private final Logger logger = Logger.getLogger(GlobalErrorHandler.class.getName());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public String handleUserNotFound(final HttpServletRequest request, final Exception error) {
        logger.severe(error.getMessage() + " " + request.getRequestURI() + " " + request.getMethod());
        return "User not Found";
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(WorkoutNotFoundException.class)
    public String handleWorkoutNotFound(final HttpServletRequest request, final Exception error) {
        logger.severe(error.getMessage() + " " + request.getRequestURI() + " " + request.getMethod());
        return "Workout not found";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExerciseNotFoundException.class)
    public String handleUserAlreadyExists(final HttpServletRequest request, final Exception error) {
        logger.severe(error.getMessage() + " " + request.getRequestURI() + " " + request.getMethod());
        return "Exercise not found";
    }

}

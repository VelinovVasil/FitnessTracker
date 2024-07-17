package com.fitnesstracker.fitnesstracker.models.dto;


import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class WorkoutExerciseDTO {


    @Positive(message = "Sets must be a positive integer")
    private int sets;

    @Positive(message = "Reps must be a positive integer")
    private int reps;

    private Long exerciseId;

}

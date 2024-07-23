package com.fitnesstracker.fitnesstracker.models.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.List;

@Data
public class WorkoutDTO {

    private Long id;

    @NotBlank(message = "Workout name must not be blank")
    private String name;

    @NotBlank(message = "Workout description must not be blank")
    private String description;

    @Positive(message = "Workout duration must be positive")
    private int duration;

    private Long userId;

    private List<WorkoutExerciseDTO> workoutExercises;

}

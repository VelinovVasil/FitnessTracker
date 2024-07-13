package com.fitnesstracker.fitnesstracker.models.dto;


import lombok.Data;

import java.util.List;

@Data
public class WorkoutDTO {

    private Long id;

    private String name;

    private String description;

    private int duration;

    private Long userId;

    private List<WorkoutExerciseDTO> workoutExercises;

}

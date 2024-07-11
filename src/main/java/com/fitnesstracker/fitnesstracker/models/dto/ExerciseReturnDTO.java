package com.fitnesstracker.fitnesstracker.models.dto;

import com.fitnesstracker.fitnesstracker.models.entity.MuscleGroup;
import lombok.Data;

@Data
public class ExerciseReturnDTO {

    private Long id;

    private String name;

    private String description;

    private MuscleGroup muscleGroup;
}

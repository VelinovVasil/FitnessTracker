package com.fitnesstracker.fitnesstracker.models.dto;


import com.fitnesstracker.fitnesstracker.models.entity.MuscleGroup;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ExerciseDTO {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Description must not be blank")
    @Size(min = 5, message = "Description must be at lest 5 chars long")
    private String description;

    private MuscleGroup muscleGroup;
}

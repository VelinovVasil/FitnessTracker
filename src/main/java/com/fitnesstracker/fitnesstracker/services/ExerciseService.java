package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.ExerciseDTO;

public interface ExerciseService {

    ExerciseDTO createExercise(ExerciseDTO exerciseDTO);

    boolean deleteExercise(Long exerciseId);

    ExerciseDTO editExercise(Long exerciseId, ExerciseDTO exerciseDTO);
}

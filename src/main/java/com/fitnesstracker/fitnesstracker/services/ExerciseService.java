package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.ExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.ExerciseReturnDTO;

import java.util.List;

public interface ExerciseService {

    ExerciseDTO createExercise(ExerciseDTO exerciseDTO);

    boolean deleteExercise(Long exerciseId);

    ExerciseDTO editExercise(Long exerciseId, ExerciseDTO exerciseDTO);

    List<ExerciseReturnDTO> getAllExercises();

    ExerciseReturnDTO getExerciseById(Long id);
}

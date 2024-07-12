package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.WorkoutExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.entity.WorkoutExercise;

import java.util.List;

public interface WorkoutExerciseService {

    void saveAll(List<WorkoutExercise> workoutExercises);

}

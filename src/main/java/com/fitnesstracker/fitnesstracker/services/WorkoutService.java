package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutShortDTO;

import java.util.List;

public interface WorkoutService {

    void createWorkout(WorkoutCreateDTO workoutCreateDTO);

    List<WorkoutShortDTO> getAllWorkoutsByUserId(Long userId);

    WorkoutDTO getWorkoutById(Long id);

    void deleteWorkoutById(Long id);

    void updateWorkout(Long id, WorkoutDTO workoutDTO);
}

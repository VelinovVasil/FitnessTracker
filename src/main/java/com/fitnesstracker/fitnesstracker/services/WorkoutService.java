package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutShortDTO;

import java.util.List;

public interface WorkoutService {

    void createWorkout(WorkoutCreateDTO workoutCreateDTO);

    List<WorkoutShortDTO> getAllWorkoutsByUserId(Long userId);
}

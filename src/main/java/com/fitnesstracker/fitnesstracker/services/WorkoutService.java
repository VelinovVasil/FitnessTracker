package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;

public interface WorkoutService {

    void createWorkout(WorkoutCreateDTO workoutCreateDTO);
}

package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.WorkoutExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Workout;
import com.fitnesstracker.fitnesstracker.models.entity.WorkoutExercise;

import java.util.List;

public interface WorkoutExerciseService {

    void saveAll(Iterable<WorkoutExercise> workoutExercises);

    void deleteAll(List<WorkoutExercise> workoutExercises);

}

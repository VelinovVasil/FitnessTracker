package com.fitnesstracker.fitnesstracker.services.impl;

import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Workout;
import com.fitnesstracker.fitnesstracker.models.entity.WorkoutExercise;
import com.fitnesstracker.fitnesstracker.repositories.ExerciseRepository;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.repositories.WorkoutExerciseRepository;
import com.fitnesstracker.fitnesstracker.repositories.WorkoutRepository;
import com.fitnesstracker.fitnesstracker.services.WorkoutExerciseService;
import com.fitnesstracker.fitnesstracker.services.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    private final UserRepository userRepository;

    private final ExerciseRepository exerciseRepository;

    private final WorkoutExerciseService workoutExerciseService;

    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public void createWorkout(WorkoutCreateDTO workoutCreateDTO) {

        Workout workout = this.modelMapper.map(workoutCreateDTO, Workout.class);
        workout.setCreatedBy(userRepository.findById(workoutCreateDTO.getUserId()).get());

        workout = this.workoutRepository.save(workout);

        Workout finalWorkout = workout;
        List<WorkoutExercise> workoutExercises = workoutCreateDTO
                .getExercises()
                .stream()
                .map(e -> {
                    WorkoutExercise workoutExercise = this.modelMapper.map(e, WorkoutExercise.class);
                    workoutExercise.setWorkout(finalWorkout);
                    workoutExercise.setExercise(this.exerciseRepository.findById(e.getExerciseId()).get());
                    return workoutExercise;
                }).toList();

        this.workoutExerciseService.saveAll(workoutExercises);
    }
}

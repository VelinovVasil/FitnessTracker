package com.fitnesstracker.fitnesstracker.services.impl;

import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutShortDTO;
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
import java.util.Set;
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
        workout.setWorkoutExercises(null);

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

    @Override
    public List<WorkoutShortDTO> getAllWorkoutsByUserId(Long userId) {
        return this.workoutRepository
                .findAllByCreatedById(userId)
                .stream()
                .map(w -> this.modelMapper.map(w, WorkoutShortDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutDTO getWorkoutById(Long id) {

        Workout workout = this.workoutRepository
                            .findById(id)
                            .get();

        WorkoutDTO workoutDTO =  this.modelMapper
                                    .map(workout,
                                    WorkoutDTO.class);

        workoutDTO.setUserId(workout.getCreatedBy().getId());

        List<WorkoutExerciseDTO> workoutExerciseDTOs = workout.getWorkoutExercises().stream().map(w -> {
                                                            WorkoutExerciseDTO dto = this.modelMapper.map(w, WorkoutExerciseDTO.class);
                                                            dto.setExerciseId(w.getExercise().getId());
                                                            return dto;
                                                        }).toList();

        workoutDTO.setWorkoutExercises(workoutExerciseDTOs);

        return workoutDTO;
    }

    @Override
    public void deleteWorkoutById(Long id) {

        List<WorkoutExercise> workoutExerciseIds = this.workoutRepository
                .findById(id)
                .get()
                .getWorkoutExercises()
                .stream()
                .toList();

        this.workoutExerciseService.deleteAll(workoutExerciseIds);
        this.workoutRepository.deleteById(id);
    }

    @Override
    public void updateWorkout(Long id, WorkoutDTO workoutDTO) {

        Workout workout  = this.workoutRepository.findById(id).get();

        workout.setName(workoutDTO.getName());
        workout.setDescription(workoutDTO.getDescription());
        workout.setDuration(workoutDTO.getDuration());

        Set<WorkoutExercise> workoutExercises = workoutDTO
                .getWorkoutExercises()
                .stream()
                .map(e -> {
                    WorkoutExercise workoutExercise = this.modelMapper.map(e, WorkoutExercise.class);
                    workoutExercise.setWorkout(workout);
                    workoutExercise.setExercise(this.exerciseRepository.findById(e.getExerciseId()).get());
                    return workoutExercise;
                }).collect(Collectors.toSet());


        workout.setWorkoutExercises(workoutExercises);

        this.workoutRepository.save(workout);
        this.workoutExerciseService.saveAll(workoutExercises);
    }

    @Override
    public List<WorkoutShortDTO> getAllWorkouts() {
        return this.workoutRepository
                .findAll()
                .stream()
                .map(w -> this.modelMapper.map(w, WorkoutShortDTO.class))
                .collect(Collectors.toList());
    }
}

package com.fitnesstracker.fitnesstracker.services.impl;


import com.fitnesstracker.fitnesstracker.handler.exceptions.ExerciseNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.ExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.ExerciseReturnDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Exercise;
import com.fitnesstracker.fitnesstracker.repositories.ExerciseRepository;
import com.fitnesstracker.fitnesstracker.services.ExerciseService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final ModelMapper modelMapper;

    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {

        Exercise exercise = this.modelMapper.map(exerciseDTO, Exercise.class);
        exercise.setId(null);
        this.exerciseRepository.save(exercise);

        return exerciseDTO;
    }

    public boolean deleteExercise(Long exerciseId) {

        Optional<Exercise> optional = this.exerciseRepository.findById(exerciseId);

        if (optional.isEmpty()) {
            throw new ExerciseNotFoundException("Exercise with such id not found");
        }

        this.exerciseRepository.delete(optional.get());
        return true;
    }

    public ExerciseDTO editExercise(Long exerciseId, ExerciseDTO exerciseDTO) {

        Optional<Exercise> optional = this.exerciseRepository.findById(exerciseId);

        if (optional.isEmpty()) {
            throw new ExerciseNotFoundException("Exercise with such id not found");
        }

        Exercise toEdit = this.modelMapper.map(exerciseDTO, Exercise.class);
        toEdit.setId(exerciseId);

        this.exerciseRepository.save(toEdit);
        return exerciseDTO;
    }

    @Override
    public List<ExerciseReturnDTO> getAllExercises() {
        return this.exerciseRepository
                .findAll()
                .stream()
                .map(e -> this.modelMapper.map(e, ExerciseReturnDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseReturnDTO getExerciseById(Long id) {
        return this.modelMapper.map(this.exerciseRepository.findById(id).orElseThrow(() -> new ExerciseNotFoundException("Exercise with such id not found")), ExerciseReturnDTO.class);
    }
}

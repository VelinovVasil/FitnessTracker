package com.fitnesstracker.fitnesstracker.services.impl;


import com.fitnesstracker.fitnesstracker.models.dto.ExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Exercise;
import com.fitnesstracker.fitnesstracker.repositories.ExerciseRepository;
import com.fitnesstracker.fitnesstracker.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;

    private final ModelMapper modelMapper;

    public ExerciseDTO createExercise(ExerciseDTO exerciseDTO) {

        Exercise exercise = this.modelMapper.map(exerciseDTO, Exercise.class);
        this.exerciseRepository.save(exercise);

        return exerciseDTO;
    }

    public boolean deleteExercise(Long exerciseId) {

        Optional<Exercise> optional = this.exerciseRepository.findById(exerciseId);

        if (optional.isEmpty()) {
            return false;
        }

        this.exerciseRepository.delete(optional.get());
        return true;
    }

    public ExerciseDTO editExercise(Long exerciseId, ExerciseDTO exerciseDTO) {

        Optional<Exercise> optional = this.exerciseRepository.findById(exerciseId);

        if (optional.isEmpty()) {
            // ToDo: throw error
        }

        Exercise toEdit = this.modelMapper.map(exerciseDTO, Exercise.class);
        toEdit.setId(exerciseId);

        this.exerciseRepository.save(toEdit);
        return exerciseDTO;
    }
}

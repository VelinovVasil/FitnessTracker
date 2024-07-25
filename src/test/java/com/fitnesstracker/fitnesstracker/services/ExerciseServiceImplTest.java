package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.handler.exceptions.ExerciseNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.ExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.ExerciseReturnDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Exercise;
import com.fitnesstracker.fitnesstracker.repositories.ExerciseRepository;
import com.fitnesstracker.fitnesstracker.services.impl.ExerciseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExerciseServiceImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    private Exercise exercise;
    private ExerciseDTO exerciseDTO;
    private ExerciseReturnDTO exerciseReturnDTO;

    @BeforeEach
    public void setUp() {
        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Push-up");

        exerciseDTO = new ExerciseDTO();
        exerciseDTO.setName("Push-up");

        exerciseReturnDTO = new ExerciseReturnDTO();
        exerciseReturnDTO.setName("Push-up");
    }

    @Test
    public void createExercise_success() {
        when(modelMapper.map(exerciseDTO, Exercise.class)).thenReturn(exercise);
        when(exerciseRepository.save(exercise)).thenReturn(exercise);

        ExerciseDTO result = exerciseService.createExercise(exerciseDTO);

        assertEquals(exerciseDTO.getName(), result.getName());
        verify(exerciseRepository, times(1)).save(exercise);
    }

    @Test
    public void deleteExercise_success() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

        boolean result = exerciseService.deleteExercise(1L);

        assertTrue(result);
        verify(exerciseRepository, times(1)).delete(exercise);
    }

    @Test
    public void deleteExercise_notFound() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> exerciseService.deleteExercise(1L));
    }

    @Test
    public void getExerciseById_success() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(modelMapper.map(exercise, ExerciseReturnDTO.class)).thenReturn(exerciseReturnDTO);

        ExerciseReturnDTO result = exerciseService.getExerciseById(1L);

        assertEquals(exerciseReturnDTO.getName(), result.getName());
    }

    @Test
    public void getExerciseById_notFound() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ExerciseNotFoundException.class, () -> exerciseService.getExerciseById(1L));
    }
}
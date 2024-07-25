package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.entity.WorkoutExercise;
import com.fitnesstracker.fitnesstracker.repositories.WorkoutExerciseRepository;
import com.fitnesstracker.fitnesstracker.services.impl.WorkoutExerciseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WorkoutExerciseServiceImplTest {

    @Mock
    private WorkoutExerciseRepository workoutExerciseRepository;

    @InjectMocks
    private WorkoutExerciseServiceImpl workoutExerciseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveAll() {

        WorkoutExercise exercise1 = new WorkoutExercise();
        WorkoutExercise exercise2 = new WorkoutExercise();
        List<WorkoutExercise> exercises = Arrays.asList(exercise1, exercise2);


        workoutExerciseService.saveAll(exercises);


        verify(workoutExerciseRepository).saveAll(exercises);
    }

    @Test
    public void testDeleteAll() {

        WorkoutExercise exercise1 = new WorkoutExercise();
        WorkoutExercise exercise2 = new WorkoutExercise();
        List<WorkoutExercise> exercises = Arrays.asList(exercise1, exercise2);


        workoutExerciseService.deleteAll(exercises);


        verify(workoutExerciseRepository).deleteAll(exercises);
    }
}


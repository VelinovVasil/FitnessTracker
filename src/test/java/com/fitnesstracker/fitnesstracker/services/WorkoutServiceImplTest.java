package com.fitnesstracker.fitnesstracker.services;


import com.fitnesstracker.fitnesstracker.handler.exceptions.ExerciseNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.WorkoutExerciseNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.WorkoutNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutShortDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Exercise;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.models.entity.Workout;
import com.fitnesstracker.fitnesstracker.models.entity.WorkoutExercise;
import com.fitnesstracker.fitnesstracker.repositories.ExerciseRepository;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.repositories.WorkoutExerciseRepository;
import com.fitnesstracker.fitnesstracker.repositories.WorkoutRepository;
import com.fitnesstracker.fitnesstracker.services.WorkoutExerciseService;
import com.fitnesstracker.fitnesstracker.services.impl.WorkoutServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WorkoutServiceImplTest {

    @InjectMocks
    private WorkoutServiceImpl workoutService;

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private WorkoutExerciseService workoutExerciseService;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateWorkout_Success() {

        WorkoutCreateDTO workoutCreateDTO = new WorkoutCreateDTO();
        workoutCreateDTO.setUserId(1L);
        workoutCreateDTO.setExercises(new ArrayList<>());

        User user = new User();
        user.setId(1L);

        Workout workout = new Workout();
        WorkoutExercise workoutExercise = new WorkoutExercise();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(workoutCreateDTO, Workout.class)).thenReturn(workout);
        when(exerciseRepository.findById(anyLong())).thenReturn(Optional.of(new Exercise()));

        workoutService.createWorkout(workoutCreateDTO);

        verify(workoutRepository, times(1)).save(any(Workout.class));
        verify(workoutExerciseService, times(1)).saveAll(anyList());
    }


    @Test
    public void testCreateWorkout_UserNotFound() {
        WorkoutCreateDTO workoutCreateDTO = new WorkoutCreateDTO();
        workoutCreateDTO.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> workoutService.createWorkout(workoutCreateDTO));
    }

    @Test
    public void testGetAllWorkoutsByUserId_Success() {
        Long userId = 1L;
        List<Workout> workouts = List.of(new Workout());
        when(workoutRepository.findAllByCreatedById(userId)).thenReturn(workouts);
        when(modelMapper.map(any(Workout.class), eq(WorkoutShortDTO.class))).thenReturn(new WorkoutShortDTO());

        List<WorkoutShortDTO> result = workoutService.getAllWorkoutsByUserId(userId);

        assertNotNull(result);
        assertEquals(workouts.size(), result.size());
    }

    @Test
    public void testGetWorkoutById_Success() {
        Long workoutId = 1L;

        Workout workout = new Workout();
        workout.setId(workoutId);
        User user = new User();
        user.setId(workoutId);
        workout.setCreatedBy(user);

        Set<WorkoutExercise> workoutExercises = new HashSet<>();
        workout.setWorkoutExercises(workoutExercises);

        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setWorkoutExercises(new ArrayList<>());

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(modelMapper.map(workout, WorkoutDTO.class)).thenReturn(workoutDTO);

        WorkoutDTO result = workoutService.getWorkoutById(workoutId);

        assertNotNull(result);
        assertNotNull(result.getWorkoutExercises());
    }



    @Test
    public void testGetWorkoutById_WorkoutNotFound() {
        Long workoutId = 1L;

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        assertThrows(WorkoutNotFoundException.class, () -> workoutService.getWorkoutById(workoutId));
    }

    @Test
    public void testDeleteWorkoutById_Success() {
        Long workoutId = 1L;
        Workout workout = new Workout();
        workout.setWorkoutExercises(Set.of(new WorkoutExercise()));

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));

        workoutService.deleteWorkoutById(workoutId);

        verify(workoutExerciseService, times(1)).deleteAll(anyList());
        verify(workoutRepository, times(1)).deleteById(workoutId);
    }

    @Test
    public void testUpdateWorkout_Success() {
        Long workoutId = 1L;
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setWorkoutExercises(new ArrayList<>());
        Workout workout = new Workout();

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workout));
        when(modelMapper.map(workoutDTO, Workout.class)).thenReturn(workout);

        workoutService.updateWorkout(workoutId, workoutDTO);

        verify(workoutRepository, times(1)).save(workout);
    }


    @Test
    public void testUpdateWorkout_WorkoutNotFound() {
        Long workoutId = 1L;
        WorkoutDTO workoutDTO = new WorkoutDTO();

        when(workoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        assertThrows(WorkoutNotFoundException.class, () -> workoutService.updateWorkout(workoutId, workoutDTO));
    }
}


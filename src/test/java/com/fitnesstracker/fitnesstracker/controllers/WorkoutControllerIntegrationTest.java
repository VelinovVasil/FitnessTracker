package com.fitnesstracker.fitnesstracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstracker.fitnesstracker.models.dto.*;
import com.fitnesstracker.fitnesstracker.services.WorkoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WorkoutControllerIntegrationTest {

    private MockMvc mockMvc;

    @MockBean
    private WorkoutService workoutService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new WorkoutController(workoutService)).build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllWorkouts() throws Exception {
        WorkoutShortDTO workout = new WorkoutShortDTO();
        workout.setId(1L);
        workout.setName("Morning Routine");
        workout.setDescription("Full body workout");
        workout.setDuration(60);

        when(workoutService.getAllWorkouts()).thenReturn(Collections.singletonList(workout));

        mockMvc.perform(get("/workout/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Morning Routine"))
                .andExpect(jsonPath("$[0].description").value("Full body workout"))
                .andExpect(jsonPath("$[0].duration").value(60));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCreateWorkout() throws Exception {
        WorkoutCreateDTO workoutCreateDTO = new WorkoutCreateDTO();
        workoutCreateDTO.setName("Morning Routine");
        workoutCreateDTO.setDescription("Full body workout");
        workoutCreateDTO.setDuration(60);
        workoutCreateDTO.setUserId(1L);


        WorkoutExerciseDTO exerciseDTO = new WorkoutExerciseDTO();
        exerciseDTO.setSets(3);
        exerciseDTO.setReps(10);
        exerciseDTO.setExerciseId(1L);

        workoutCreateDTO.setExercises(Collections.singletonList(exerciseDTO));

        mockMvc.perform(post("/workout/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutCreateDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllWorkoutsByUserId() throws Exception {
        WorkoutShortDTO workout = new WorkoutShortDTO();
        workout.setId(1L);
        workout.setName("Morning Routine");
        workout.setDescription("Full body workout");
        workout.setDuration(60);

        when(workoutService.getAllWorkoutsByUserId(anyLong())).thenReturn(Collections.singletonList(workout));

        mockMvc.perform(get("/workout/get/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Morning Routine"))
                .andExpect(jsonPath("$[0].description").value("Full body workout"))
                .andExpect(jsonPath("$[0].duration").value(60));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetWorkoutById() throws Exception {
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setId(1L);
        workoutDTO.setName("Morning Routine");
        workoutDTO.setDescription("Full body workout");
        workoutDTO.setDuration(60);
        workoutDTO.setUserId(1L);


        WorkoutExerciseDTO exerciseDTO = new WorkoutExerciseDTO();
        exerciseDTO.setSets(3);
        exerciseDTO.setReps(10);
        exerciseDTO.setExerciseId(1L);

        workoutDTO.setWorkoutExercises(Collections.singletonList(exerciseDTO));

        when(workoutService.getWorkoutById(anyLong())).thenReturn(workoutDTO);

        mockMvc.perform(get("/workout/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Morning Routine"))
                .andExpect(jsonPath("$.description").value("Full body workout"))
                .andExpect(jsonPath("$.duration").value(60))
                .andExpect(jsonPath("$.userId").value(1L))
                .andExpect(jsonPath("$.workoutExercises[0].sets").value(3))
                .andExpect(jsonPath("$.workoutExercises[0].reps").value(10))
                .andExpect(jsonPath("$.workoutExercises[0].exerciseId").value(1L));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteWorkoutById() throws Exception {
        mockMvc.perform(delete("/workout/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testUpdateWorkout() throws Exception {
        WorkoutDTO workoutDTO = new WorkoutDTO();
        workoutDTO.setId(1L);
        workoutDTO.setName("Morning Routine");
        workoutDTO.setDescription("Full body workout");
        workoutDTO.setDuration(60);
        workoutDTO.setUserId(1L);


        WorkoutExerciseDTO exerciseDTO = new WorkoutExerciseDTO();
        exerciseDTO.setSets(3);
        exerciseDTO.setReps(10);
        exerciseDTO.setExerciseId(1L);

        workoutDTO.setWorkoutExercises(Collections.singletonList(exerciseDTO));

        mockMvc.perform(put("/workout/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(workoutDTO)))
                .andExpect(status().isNoContent());
    }
}

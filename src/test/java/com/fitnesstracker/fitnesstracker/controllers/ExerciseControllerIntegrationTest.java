package com.fitnesstracker.fitnesstracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstracker.fitnesstracker.models.dto.ExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.ExerciseReturnDTO;
import com.fitnesstracker.fitnesstracker.models.entity.MuscleGroup;
import com.fitnesstracker.fitnesstracker.services.ExerciseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
public class ExerciseControllerIntegrationTest {

    private MockMvc mockMvc;

    @MockBean
    private ExerciseService exerciseService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ExerciseController(exerciseService)).build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void testGetAllExercises() throws Exception {
        ExerciseReturnDTO exercise = new ExerciseReturnDTO();
        exercise.setId(1L);
        exercise.setName("Push Up");
        exercise.setDescription("A basic exercise for upper body strength.");
        exercise.setMuscleGroup(MuscleGroup.CHEST);

        when(exerciseService.getAllExercises()).thenReturn(Collections.singletonList(exercise));

        mockMvc.perform(get("/exercise/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Push Up"))
                .andExpect(jsonPath("$[0].description").value("A basic exercise for upper body strength."))
                .andExpect(jsonPath("$[0].muscleGroup").value(MuscleGroup.CHEST.name()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testCreateExercise() throws Exception {
        ExerciseDTO exerciseDTO = new ExerciseDTO();
        exerciseDTO.setName("Squat");
        exerciseDTO.setDescription("A basic lower body exercise.");
        exerciseDTO.setMuscleGroup(MuscleGroup.LEGS);

        mockMvc.perform(post("/exercise/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(exerciseDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void testGetExerciseById() throws Exception {
        ExerciseReturnDTO exercise = new ExerciseReturnDTO();
        exercise.setId(1L);
        exercise.setName("Pull Up");
        exercise.setDescription("An upper body strength exercise.");
        exercise.setMuscleGroup(MuscleGroup.BACK);

        when(exerciseService.getExerciseById(anyLong())).thenReturn(exercise);

        mockMvc.perform(get("/exercise/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Pull Up"))
                .andExpect(jsonPath("$.description").value("An upper body strength exercise."))
                .andExpect(jsonPath("$.muscleGroup").value(MuscleGroup.BACK.name()));
    }
}


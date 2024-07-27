package com.fitnesstracker.fitnesstracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstracker.fitnesstracker.models.dto.RecipeDetailDTO;
import com.fitnesstracker.fitnesstracker.models.dto.RecipeShortDTO;
import com.fitnesstracker.fitnesstracker.models.entity.RecipeType;
import com.fitnesstracker.fitnesstracker.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SpringJUnitConfig
public class RecipeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    private RecipeDetailDTO recipeDetailDTO;
    private RecipeShortDTO recipeShortDTO;

    @BeforeEach
    public void setUp() {
        recipeDetailDTO = new RecipeDetailDTO();
        recipeShortDTO = new RecipeShortDTO();
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    public void testGetAllRecipes() throws Exception {
        when(recipeService.getAllRecipes()).thenReturn(List.of(recipeShortDTO));

        mockMvc.perform(get("/recipe/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists());
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    public void testAddRecipe() throws Exception {
        RecipeDetailDTO recipeDetailDTO = RecipeDetailDTO.builder()
                .name("Delicious Cake")
                .recipeType(RecipeType.DESSERT)
                .description("A very tasty cake recipe.")
                .instructions("Mix ingredients and bake.")
                .calories(250)
                .userId(1L)
                .build();

        when(recipeService.createRecipe(any(RecipeDetailDTO.class))).thenReturn(recipeDetailDTO);

        mockMvc.perform(post("/recipe/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipeDetailDTO)))
                .andExpect(status().isOk()) // Expecting status 200 OK
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(recipeDetailDTO.getName()))
                .andExpect(jsonPath("$.recipeType").value(recipeDetailDTO.getRecipeType().name()))
                .andExpect(jsonPath("$.description").value(recipeDetailDTO.getDescription()))
                .andExpect(jsonPath("$.instructions").value(recipeDetailDTO.getInstructions()))
                .andExpect(jsonPath("$.calories").value(recipeDetailDTO.getCalories()))
                .andExpect(jsonPath("$.userId").value(recipeDetailDTO.getUserId()))
                .andExpect(jsonPath("$.id").doesNotExist()); // Adjust based on your DTO fields
    }


    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    public void testGetAllRecipesByUser() throws Exception {
        when(recipeService.getAllRecipesByUserId(anyLong())).thenReturn(List.of(recipeShortDTO));

        mockMvc.perform(get("/recipe/get/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists());
    }

    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    public void testGetRecipeById() throws Exception {
        RecipeDetailDTO recipeDetailDTO = RecipeDetailDTO.builder()
                .name("Delicious Cake")
                .recipeType(RecipeType.DESSERT)
                .description("A very tasty cake recipe.")
                .instructions("Mix ingredients and bake.")
                .calories(250)
                .userId(1L)
                .build();


        when(recipeService.getRecipeById(anyLong())).thenReturn(recipeDetailDTO);

        mockMvc.perform(get("/recipe/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(recipeDetailDTO.getName()))
                .andExpect(jsonPath("$.recipeType").value(recipeDetailDTO.getRecipeType().name()))
                .andExpect(jsonPath("$.description").value(recipeDetailDTO.getDescription()))
                .andExpect(jsonPath("$.instructions").value(recipeDetailDTO.getInstructions()))
                .andExpect(jsonPath("$.calories").value(recipeDetailDTO.getCalories()))
                .andExpect(jsonPath("$.userId").value(recipeDetailDTO.getUserId()));
    }


    @WithMockUser(username = "testuser", roles = {"USER"})
    @Test
    public void testDeleteRecipeById() throws Exception {
        doNothing().when(recipeService).deleteRecipeById(anyLong());

        mockMvc.perform(delete("/recipe/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    public void testUpdateRecipe() throws Exception {
        // Example valid request payload
        String requestBody = "{"
                + "\"name\": \"Delicious Cake\","
                + "\"recipeType\": \"DESSERT\","
                + "\"description\": \"A very tasty cake recipe.\","
                + "\"instructions\": \"Mix ingredients and bake.\","
                + "\"calories\": 250,"
                + "\"userId\": 1"
                + "}";

        mockMvc.perform(put("/recipe/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNoContent());
    }

}

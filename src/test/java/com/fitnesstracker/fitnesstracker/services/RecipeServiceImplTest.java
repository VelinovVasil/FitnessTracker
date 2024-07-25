package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.handler.exceptions.RecipeNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.RecipeDetailDTO;
import com.fitnesstracker.fitnesstracker.models.dto.RecipeShortDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Recipe;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.repositories.RecipeRepository;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.impl.RecipeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceImplTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RecipeServiceImpl recipeService;

    private User user;
    private Recipe recipe;
    private RecipeDetailDTO recipeDetailDTO;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Test Recipe");
        recipe.setUser(user);

        recipeDetailDTO = new RecipeDetailDTO();
        recipeDetailDTO.setUserId(user.getId());
        recipeDetailDTO.setName("Test Recipe");
    }

    @Test
    public void createRecipe_success() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(modelMapper.map(any(RecipeDetailDTO.class), eq(Recipe.class))).thenReturn(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        RecipeDetailDTO result = recipeService.createRecipe(recipeDetailDTO);

        // Verify results
        assertNotNull(result);
        assertEquals("Test Recipe", result.getName());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }


    @Test
    public void createRecipe_userNotFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            recipeService.createRecipe(recipeDetailDTO);
        });

        assertEquals("User associated with this recipe not found", thrown.getMessage());
        verify(recipeRepository, never()).save(any(Recipe.class));
    }

    @Test
    public void getAllRecipesByUserId_success() {
        when(recipeRepository.findAllByUserId(any(Long.class))).thenReturn(Arrays.asList(recipe));
        when(modelMapper.map(any(Recipe.class), eq(RecipeShortDTO.class))).thenReturn(new RecipeShortDTO());

        assertNotNull(recipeService.getAllRecipesByUserId(user.getId()));
        verify(recipeRepository, times(1)).findAllByUserId(user.getId());
    }

    @Test
    public void getRecipeById_success() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));
        when(modelMapper.map(any(Recipe.class), eq(RecipeDetailDTO.class))).thenReturn(recipeDetailDTO);

        RecipeDetailDTO result = recipeService.getRecipeById(recipe.getId());

        assertNotNull(result);
        assertEquals("Test Recipe", result.getName());
        verify(recipeRepository, times(1)).findById(recipe.getId());
    }

    @Test
    public void getRecipeById_notFound() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        RecipeNotFoundException thrown = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.getRecipeById(recipe.getId());
        });

        assertEquals("Not recipe with such id found", thrown.getMessage());
        verify(recipeRepository, times(1)).findById(recipe.getId());
    }

    @Test
    public void deleteRecipeById_success() {
        doNothing().when(recipeRepository).deleteById(any(Long.class));

        recipeService.deleteRecipeById(recipe.getId());

        verify(recipeRepository, times(1)).deleteById(recipe.getId());
    }

    @Test
    public void updateRecipe_success() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.of(recipe));
        when(modelMapper.map(any(RecipeDetailDTO.class), eq(Recipe.class))).thenReturn(recipe);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);

        recipeService.updateRecipe(recipe.getId(), recipeDetailDTO);

        verify(recipeRepository, times(1)).findById(recipe.getId());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void updateRecipe_notFound() {
        when(recipeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        RecipeNotFoundException thrown = assertThrows(RecipeNotFoundException.class, () -> {
            recipeService.updateRecipe(recipe.getId(), recipeDetailDTO);
        });

        assertEquals("Not recipe with such id found", thrown.getMessage());
        verify(recipeRepository, times(1)).findById(recipe.getId());
        verify(recipeRepository, never()).save(any(Recipe.class));
    }

    @Test
    public void getAllRecipes_success() {
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));
        when(modelMapper.map(any(Recipe.class), eq(RecipeShortDTO.class))).thenReturn(new RecipeShortDTO());

        assertNotNull(recipeService.getAllRecipes());
        verify(recipeRepository, times(1)).findAll();
    }
}


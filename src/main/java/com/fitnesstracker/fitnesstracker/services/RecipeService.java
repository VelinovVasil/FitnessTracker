package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.RecipeDetailDTO;
import com.fitnesstracker.fitnesstracker.models.dto.RecipeShortDTO;

import java.util.List;

public interface RecipeService {

    RecipeDetailDTO createRecipe(RecipeDetailDTO recipeDetailDTO);

    List<RecipeShortDTO> getAllRecipesByUserId(Long userId);

    RecipeDetailDTO getRecipeById(Long recipeId);
}

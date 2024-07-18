package com.fitnesstracker.fitnesstracker.services.impl;


import com.fitnesstracker.fitnesstracker.models.dto.RecipeDetailDTO;
import com.fitnesstracker.fitnesstracker.models.dto.RecipeShortDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Recipe;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.repositories.RecipeRepository;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    public RecipeDetailDTO createRecipe(RecipeDetailDTO recipeDetailDTO) {

        Optional<User> userOptional = this.userRepository.findById(recipeDetailDTO.getUserId());

        if (userOptional.isEmpty()) {
            // ToDo: Throw an error
        }

        Recipe recipe = this.modelMapper.map(recipeDetailDTO, Recipe.class);
        recipe.setUser(userOptional.get());
        recipe.setType(recipeDetailDTO.getRecipeType());

        this.recipeRepository.save(recipe);

        return recipeDetailDTO;
    }

    @Override
    public List<RecipeShortDTO> getAllRecipesByUserId(Long userId) {

        List<Recipe> allByUserId = this.recipeRepository.findAllByUserId(userId);

        return allByUserId
                .stream()
                .map(r -> this.modelMapper.map(r, RecipeShortDTO.class))
                .collect(Collectors.toList());

    }

    @Override
    public RecipeDetailDTO getRecipeById(Long recipeId) {

        Optional<Recipe> recipe = this.recipeRepository.findById(recipeId);

        if (recipe.isEmpty()) {
            // ToDo: throw an error
        }

        return this.modelMapper.map(recipe.get(), RecipeDetailDTO.class);
    }

    @Override
    public void deleteRecipeById(Long id) {
        this.recipeRepository.deleteById(id);
    }

    @Override
    public void updateRecipe(Long id, RecipeDetailDTO recipeDetailDTO) {

        Optional<Recipe> optional = this.recipeRepository.findById(id);

        if (optional.isEmpty()) {
            // ToDo: throw an error
        }

        Recipe recipe = optional.get();

        Recipe editedRecipe = this.modelMapper.map(recipeDetailDTO, Recipe.class);
        editedRecipe.setId(recipe.getId());

        this.recipeRepository.save(editedRecipe);
    }

    @Override
    public List<RecipeShortDTO> getAllRecipes() {
        return this.recipeRepository
                .findAll()
                .stream()
                .map(r -> this.modelMapper.map(r, RecipeShortDTO.class))
                .collect(Collectors.toList());
    }


}

package com.fitnesstracker.fitnesstracker.services.impl;


import com.fitnesstracker.fitnesstracker.models.dto.RecipeDetailDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Recipe;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.repositories.RecipeRepository;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        this.recipeRepository.save(recipe);

        return recipeDetailDTO;
    }


}

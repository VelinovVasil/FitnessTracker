package com.fitnesstracker.fitnesstracker.models.dto;

import com.fitnesstracker.fitnesstracker.models.entity.RecipeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RecipeDetailDTO {

    @NotBlank(message = "Recipe name must not be blank")
    private String name;

    private RecipeType recipeType;

    @NotBlank(message = "Recipe description must not be blank")
    private String description;

    @NotBlank(message = "Recipe instructions must not be blank")
    private String instructions;

    @Positive(message = "Calories must be a positive integer")
    private int calories;

    @Positive
    private Long userId;

}

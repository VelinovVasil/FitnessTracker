package com.fitnesstracker.fitnesstracker.models.dto;

import com.fitnesstracker.fitnesstracker.models.entity.RecipeType;
import lombok.Data;

@Data
public class RecipeDetailDTO {

    private String name;

    private RecipeType recipeType;

    private String description;

    private String instructions;

    private int calories;

    private Long userId;

}

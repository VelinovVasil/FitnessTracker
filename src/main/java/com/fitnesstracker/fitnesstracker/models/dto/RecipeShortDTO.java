package com.fitnesstracker.fitnesstracker.models.dto;

import com.fitnesstracker.fitnesstracker.models.entity.RecipeType;
import lombok.Data;

@Data
public class RecipeShortDTO {

    private Long id;

    private String name;

    private RecipeType recipeType;

    private int calories;
}

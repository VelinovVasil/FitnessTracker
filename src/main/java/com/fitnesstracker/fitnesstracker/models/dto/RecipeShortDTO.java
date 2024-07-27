package com.fitnesstracker.fitnesstracker.models.dto;

import com.fitnesstracker.fitnesstracker.models.entity.RecipeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeShortDTO {

    private Long id;

    private String name;

    private RecipeType recipeType;

    private int calories;
}

package com.fitnesstracker.fitnesstracker.controllers;

import com.fitnesstracker.fitnesstracker.models.dto.RecipeDetailDTO;
import com.fitnesstracker.fitnesstracker.models.dto.RecipeShortDTO;
import com.fitnesstracker.fitnesstracker.services.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/create")
    public ResponseEntity<RecipeDetailDTO> addRecipe(@RequestBody RecipeDetailDTO recipeDetailDTO) {
        return ResponseEntity.ok(this.recipeService.createRecipe(recipeDetailDTO));
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/get/{userId}")
    public ResponseEntity<List<RecipeShortDTO>> getAllRecipesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.recipeService.getAllRecipesByUserId(userId));
    }

}
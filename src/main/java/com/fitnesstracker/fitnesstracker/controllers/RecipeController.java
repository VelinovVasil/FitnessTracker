package com.fitnesstracker.fitnesstracker.controllers;

import com.fitnesstracker.fitnesstracker.models.dto.RecipeDetailDTO;
import com.fitnesstracker.fitnesstracker.models.dto.RecipeShortDTO;
import com.fitnesstracker.fitnesstracker.services.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/recipe")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;


    @GetMapping("/")
    public ResponseEntity<List<RecipeShortDTO>> getAllRecipes() {
        return ResponseEntity.ok(this.recipeService.getAllRecipes());
    }


    @PostMapping("/create")
    public ResponseEntity<RecipeDetailDTO> addRecipe(@Valid @RequestBody RecipeDetailDTO recipeDetailDTO) {
        return ResponseEntity.ok(this.recipeService.createRecipe(recipeDetailDTO));
    }


    @GetMapping("/get/{userId}")
    public ResponseEntity<List<RecipeShortDTO>> getAllRecipesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(this.recipeService.getAllRecipesByUserId(userId));
    }


    @GetMapping("/{id}")
    public ResponseEntity<RecipeDetailDTO> getRecipeById(@PathVariable Long id) {
        return ResponseEntity.ok(this.recipeService.getRecipeById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) {
        this.recipeService.deleteRecipeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeDetailDTO dto) {
        this.recipeService.updateRecipe(id, dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

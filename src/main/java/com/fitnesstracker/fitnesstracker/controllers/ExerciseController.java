package com.fitnesstracker.fitnesstracker.controllers;


import com.fitnesstracker.fitnesstracker.models.dto.ExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.ExerciseReturnDTO;
import com.fitnesstracker.fitnesstracker.services.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {


    private final ExerciseService exerciseService;

//    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public ResponseEntity<List<ExerciseReturnDTO>> getAllExercises() {
        return ResponseEntity.ok(this.exerciseService.getAllExercises());
    }

//    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    public ResponseEntity<Void> createExercise(@RequestBody ExerciseDTO dto) {
        this.exerciseService.createExercise(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseReturnDTO> getExerciseById(@PathVariable Long id) {
        return ResponseEntity.ok(this.exerciseService.getExerciseById(id));
    }
}

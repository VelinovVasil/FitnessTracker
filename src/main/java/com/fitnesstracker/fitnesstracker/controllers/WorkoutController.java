package com.fitnesstracker.fitnesstracker.controllers;


import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutShortDTO;
import com.fitnesstracker.fitnesstracker.services.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping("/create")
    public ResponseEntity<Void> createWorkout(@RequestBody WorkoutCreateDTO workoutCreateDTO) {
        this.workoutService.createWorkout(workoutCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<WorkoutShortDTO>> getAllWorkoutsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(this.workoutService.getAllWorkoutsByUserId(userId));
    }
}

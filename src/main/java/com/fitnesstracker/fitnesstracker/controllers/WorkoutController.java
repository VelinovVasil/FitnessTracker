package com.fitnesstracker.fitnesstracker.controllers;


import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutExerciseDTO;
import com.fitnesstracker.fitnesstracker.models.dto.WorkoutShortDTO;
import com.fitnesstracker.fitnesstracker.services.UserSecurityService;
import com.fitnesstracker.fitnesstracker.services.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final WorkoutService workoutService;

    private final UserSecurityService userSecurityService;

    @GetMapping("/")
    public ResponseEntity<List<WorkoutShortDTO>> getAllWorkouts() {
        return ResponseEntity.ok(this.workoutService.getAllWorkouts());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createWorkout(@Valid @RequestBody WorkoutCreateDTO workoutCreateDTO) {
        this.workoutService.createWorkout(workoutCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<WorkoutShortDTO>> getAllWorkoutsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(this.workoutService.getAllWorkoutsByUserId(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutDTO> getWorkoutById(@PathVariable Long id) {
        return ResponseEntity.ok(this.workoutService.getWorkoutById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutById(@PathVariable Long id) {
        this.workoutService.deleteWorkoutById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateWorkout(@PathVariable Long id, @Valid @RequestBody WorkoutDTO workoutDTO) {
        this.workoutService.updateWorkout(id, workoutDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

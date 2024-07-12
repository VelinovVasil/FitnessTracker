package com.fitnesstracker.fitnesstracker.controllers;


import com.fitnesstracker.fitnesstracker.models.dto.WorkoutCreateDTO;
import com.fitnesstracker.fitnesstracker.services.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

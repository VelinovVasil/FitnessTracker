package com.fitnesstracker.fitnesstracker.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exercises")
@Data
@AllArgsConstructor
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MuscleGroup muscleGroup;

    @OneToMany(mappedBy = "exercise")
    private Set<WorkoutExercise> workoutExercises;

    public Exercise() {
        this.workoutExercises = new HashSet<>();
    }
}

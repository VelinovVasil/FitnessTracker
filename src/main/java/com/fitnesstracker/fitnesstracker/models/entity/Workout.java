package com.fitnesstracker.fitnesstracker.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "workouts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String description;

    @Column
    private int duration;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User createdBy;

    @OneToMany(mappedBy = "workout")
    private Set<WorkoutExercise> workoutExercises;

}

package com.fitnesstracker.fitnesstracker.repositories;


import com.fitnesstracker.fitnesstracker.models.entity.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Long> {
}

package com.fitnesstracker.fitnesstracker.repositories;


import com.fitnesstracker.fitnesstracker.models.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {


}

package com.fitnesstracker.fitnesstracker.models.dto;


import lombok.Data;

@Data
public class WorkoutShortDTO {

    private Long id;

    private String name;

    private String description;

    private int duration;
}

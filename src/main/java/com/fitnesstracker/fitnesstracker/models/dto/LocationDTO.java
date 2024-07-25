package com.fitnesstracker.fitnesstracker.models.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LocationDTO {

    @NotBlank(message = "Location name must not be blank")
    private String name;

    private double latitude;

    private double longitude;

    private Long userId;
}

package com.fitnesstracker.fitnesstracker.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class LocationReturnDTO {

    private Long id;

    private String name;

    private double latitude;

    private double longitude;

    private Long userId;
}

package com.fitnesstracker.fitnesstracker.models.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WeatherCurrent {
    private Location location;
    private Current current;
}
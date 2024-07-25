package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.WeatherCurrent;
import org.springframework.http.ResponseEntity;

public interface WeatherService {

    ResponseEntity<WeatherCurrent> getCurrentWeather(String q);
}

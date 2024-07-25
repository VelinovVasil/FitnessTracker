package com.fitnesstracker.fitnesstracker.controllers;

import com.fitnesstracker.fitnesstracker.models.dto.WeatherCurrent;
import com.fitnesstracker.fitnesstracker.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/current")
    public ResponseEntity<WeatherCurrent> getCurrentWeather(@RequestParam String q) {
        return weatherService.getCurrentWeather(q);
    }
}

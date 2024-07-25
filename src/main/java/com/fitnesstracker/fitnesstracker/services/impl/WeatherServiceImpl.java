package com.fitnesstracker.fitnesstracker.services.impl;

import com.fitnesstracker.fitnesstracker.clients.WeatherAPI;
import com.fitnesstracker.fitnesstracker.models.dto.WeatherCurrent;
import com.fitnesstracker.fitnesstracker.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherAPI weatherAPI;

    @Override
    public ResponseEntity<WeatherCurrent> getCurrentWeather(String q) {
        return ResponseEntity.ok(weatherAPI.getCurrentWeather(q));
    }

}

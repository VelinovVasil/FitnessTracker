package com.fitnesstracker.fitnesstracker.controllers;

import com.fitnesstracker.fitnesstracker.models.dto.Current;
import com.fitnesstracker.fitnesstracker.models.dto.Location;
import com.fitnesstracker.fitnesstracker.models.dto.Condition;
import com.fitnesstracker.fitnesstracker.models.dto.WeatherCurrent;
import com.fitnesstracker.fitnesstracker.services.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WeatherControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherService weatherService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testGetCurrentWeather() throws Exception {

        Location location = Location.builder()
                .name("London")
                .region("England")
                .country("United Kingdom")
                .lat("51.5074")
                .lon("-0.1278")
                .tz_id("Europe/London")
                .localtime_epoch("1596545287")
                .localtime("2020-08-04 19:48")
                .build();

        Current current = Current.builder()
                .time("2020-08-04 19:48")
                .temp_c("22.0")
                .feelslike_c("21.0")
                .condition(Condition.builder().text("Clear").build())
                .wind_kph("15.0")
                .wind_dir("N")
                .is_day("1")
                .last_updated("2020-08-04 19:48")
                .last_updated_epoch("1596545287")
                .build();

        WeatherCurrent weatherCurrent = WeatherCurrent.builder()
                .location(location)
                .current(current)
                .build();

        when(weatherService.getCurrentWeather(anyString())).thenReturn(ResponseEntity.ok(weatherCurrent));

        mockMvc.perform(get("/weather/current")
                        .param("q", "London")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.location.name").value("London"))
                .andExpect(jsonPath("$.location.region").value("England"))
                .andExpect(jsonPath("$.current.temp_c").value("22.0"))
                .andExpect(jsonPath("$.current.condition.text").value("Clear"));
    }
}

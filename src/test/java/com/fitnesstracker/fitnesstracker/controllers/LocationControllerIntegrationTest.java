package com.fitnesstracker.fitnesstracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitnesstracker.fitnesstracker.models.dto.LocationDTO;
import com.fitnesstracker.fitnesstracker.models.dto.LocationReturnDTO;
import com.fitnesstracker.fitnesstracker.services.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LocationControllerIntegrationTest {

    private MockMvc mockMvc;

    @MockBean
    private LocationService locationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new LocationController(locationService)).build();
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void testAddLocation() throws Exception {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName("Gym");
        locationDTO.setLatitude(40.7128);
        locationDTO.setLongitude(-74.0060);
        locationDTO.setUserId(1L);

        when(locationService.addLocation(any(LocationDTO.class))).thenReturn(locationDTO);

        mockMvc.perform(post("/location/add-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Gym"))
                .andExpect(jsonPath("$.latitude").value(40.7128))
                .andExpect(jsonPath("$.longitude").value(-74.0060))
                .andExpect(jsonPath("$.userId").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void testDeleteLocation() throws Exception {
        mockMvc.perform(delete("/location/delete/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void testGetAllLocationsByUserId() throws Exception {
        LocationReturnDTO location = new LocationReturnDTO();
        location.setId(1L);
        location.setName("Gym");
        location.setLatitude(40.7128);
        location.setLongitude(-74.0060);
        location.setUserId(1L);

        when(locationService.getAllLocationsByUserId(anyLong())).thenReturn(Collections.singletonList(location));

        mockMvc.perform(get("/location/user/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Gym"))
                .andExpect(jsonPath("$[0].latitude").value(40.7128))
                .andExpect(jsonPath("$[0].longitude").value(-74.0060))
                .andExpect(jsonPath("$[0].userId").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void testGetAllLocations() throws Exception {
        LocationDTO locationDTO = new LocationDTO();
        locationDTO.setName("Gym");
        locationDTO.setLatitude(40.7128);
        locationDTO.setLongitude(-74.0060);
        locationDTO.setUserId(1L);

        when(locationService.getAllLocations()).thenReturn(Collections.singletonList(locationDTO));

        mockMvc.perform(get("/location/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Gym"))
                .andExpect(jsonPath("$[0].latitude").value(40.7128))
                .andExpect(jsonPath("$[0].longitude").value(-74.0060))
                .andExpect(jsonPath("$[0].userId").value(1L));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER"})
    public void testGetLocationById() throws Exception {
        LocationReturnDTO location = new LocationReturnDTO();
        location.setId(1L);
        location.setName("Gym");
        location.setLatitude(40.7128);
        location.setLongitude(-74.0060);
        location.setUserId(1L);

        when(locationService.getLocationById(anyLong())).thenReturn(location);

        mockMvc.perform(get("/location/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Gym"))
                .andExpect(jsonPath("$.latitude").value(40.7128))
                .andExpect(jsonPath("$.longitude").value(-74.0060))
                .andExpect(jsonPath("$.userId").value(1L));
    }
}

package com.fitnesstracker.fitnesstracker.controllers;

import com.fitnesstracker.fitnesstracker.models.dto.LocationDTO;
import com.fitnesstracker.fitnesstracker.models.dto.LocationReturnDTO;
import com.fitnesstracker.fitnesstracker.services.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;


    @PostMapping("/add-location")
    public ResponseEntity<LocationDTO> addLocation(@Valid @RequestBody LocationDTO locationDTO) {
        return ResponseEntity.ok(this.locationService.addLocation(locationDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) {
        this.locationService.deleteLocation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LocationReturnDTO>> getAllLocationsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(this.locationService.getAllLocationsByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<LocationDTO>> getAllLocations() {
        return ResponseEntity.ok(this.locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationReturnDTO> getLocationById(@PathVariable Long id) {
        return ResponseEntity.ok(this.locationService.getLocationById(id));
    }
}



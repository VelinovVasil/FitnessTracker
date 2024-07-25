package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.LocationDTO;

import java.util.List;

public interface LocationService {

    LocationDTO addLocation(LocationDTO locationDTO);

    void deleteLocation(Long id);

    List<LocationDTO> getAllLocationsByUserId(Long userId);

    List<LocationDTO> getAllLocations();

    LocationDTO getLocationById(Long id);
}

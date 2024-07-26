package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.dto.LocationDTO;
import com.fitnesstracker.fitnesstracker.models.dto.LocationReturnDTO;

import java.util.List;

public interface LocationService {

    LocationDTO addLocation(LocationDTO locationDTO);

    void deleteLocation(Long id);

    List<LocationReturnDTO> getAllLocationsByUserId(Long userId);

    List<LocationDTO> getAllLocations();

    LocationReturnDTO getLocationById(Long id);
}

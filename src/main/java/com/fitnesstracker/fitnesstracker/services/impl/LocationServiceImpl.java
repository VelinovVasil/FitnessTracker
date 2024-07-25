package com.fitnesstracker.fitnesstracker.services.impl;

import com.fitnesstracker.fitnesstracker.handler.exceptions.LocationNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.LocationDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Location;
import com.fitnesstracker.fitnesstracker.repositories.LocationRepository;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Override
    public LocationDTO addLocation(LocationDTO locationDTO) {

        Location location = this.modelMapper.map(locationDTO, Location.class);
        location.setUser(this.userRepository.findById(locationDTO.getUserId()).orElseThrow(() -> new UserNotFoundException("User associated with this location not found")));

        this.locationRepository.save(location);

        return locationDTO;
    }

    @Override
    public void deleteLocation(Long id) {

        this.locationRepository.deleteById(id);
    }

    @Override
    public List<LocationDTO> getAllLocationsByUserId(Long userId) {
        return this.locationRepository
                .findAllByUserId(userId)
                .stream()
                .map(l -> {
                    LocationDTO dto = this.modelMapper.map(l, LocationDTO.class);
                    dto.setUserId(l.getUser().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public List<LocationDTO> getAllLocations() {
        return this.locationRepository
                .findAll()
                .stream()
                .map(l -> {
                    LocationDTO dto = this.modelMapper.map(l, LocationDTO.class);
                    dto.setUserId(l.getUser().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public LocationDTO getLocationById(Long id) {
        Location location = this.locationRepository
                                .findById(id)
                                .orElseThrow(() -> new LocationNotFoundException("Location with such id not found"));

        LocationDTO dto = this.modelMapper.map(location, LocationDTO.class);
        dto.setUserId(location.getUser().getId());

        return dto;
    }
}

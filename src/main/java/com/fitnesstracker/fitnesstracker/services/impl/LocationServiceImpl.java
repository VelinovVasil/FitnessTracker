package com.fitnesstracker.fitnesstracker.services.impl;

import com.fitnesstracker.fitnesstracker.handler.exceptions.LocationNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.LocationDTO;
import com.fitnesstracker.fitnesstracker.models.dto.LocationReturnDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Location;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.repositories.LocationRepository;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    @Override
    public LocationDTO addLocation(LocationDTO locationDTO) {

        Optional<User> user = this.userRepository.findById(locationDTO.getUserId());

        if (user.isEmpty()) {
            throw new UserNotFoundException("User associated with this location not found");
        }

        Location location = this.modelMapper.map(locationDTO, Location.class);
        location.setId(null);

        System.out.println("Location before save (ID should be null or not set): " + location);

        location.setUser(user.get());

        this.locationRepository.save(location);

        System.out.println("Location after save (ID should be auto-generated): " + location);

        return locationDTO;
    }


    @Override
    public void deleteLocation(Long id) {

        this.locationRepository.deleteById(id);
    }

    @Override
    public List<LocationReturnDTO> getAllLocationsByUserId(Long userId) {
        return this.locationRepository
                .findAllByUserId(userId)
                .stream()
                .map(l -> {
                    LocationReturnDTO dto = this.modelMapper.map(l, LocationReturnDTO.class);
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
    public LocationReturnDTO getLocationById(Long id) {
        Location location = this.locationRepository
                                .findById(id)
                                .orElseThrow(() -> new LocationNotFoundException("Location with such id not found"));

        LocationReturnDTO dto = this.modelMapper.map(location, LocationReturnDTO.class);
        dto.setUserId(location.getUser().getId());

        return dto;
    }
}

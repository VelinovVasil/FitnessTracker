package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.handler.exceptions.LocationNotFoundException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.LocationDTO;
import com.fitnesstracker.fitnesstracker.models.dto.LocationReturnDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Location;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.repositories.LocationRepository;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.impl.LocationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private LocationServiceImpl locationService;

    private Location location;
    private LocationDTO locationDTO;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("Test User");

        location = new Location();
        location.setId(1L);
        location.setName("Test Location");
        location.setUser(user);

        locationDTO = new LocationDTO();
        locationDTO.setName("Test Location");
        locationDTO.setUserId(1L);
    }

    @Test
    void testAddLocation_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(locationRepository.save(any(Location.class))).thenAnswer(invocation -> {
            Location savedLocation = invocation.getArgument(0);
            savedLocation.setId(1L);
            return savedLocation;
        });
        when(modelMapper.map(locationDTO, Location.class)).thenReturn(location);

        LocationDTO result = locationService.addLocation(locationDTO);

        assertNotNull(result);
        assertEquals(locationDTO.getName(), result.getName());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void testAddLocation_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> locationService.addLocation(locationDTO));
        verify(locationRepository, never()).save(any(Location.class));
    }

    @Test
    void testDeleteLocation_Success() {
        locationService.deleteLocation(1L);

        verify(locationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllLocationsByUserId_Success() {
        when(locationRepository.findAllByUserId(1L)).thenReturn(Arrays.asList(location));
        when(modelMapper.map(location, LocationReturnDTO.class)).thenReturn(new LocationReturnDTO());

        List<LocationReturnDTO> locations = locationService.getAllLocationsByUserId(1L);

        assertNotNull(locations);
        assertEquals(1, locations.size());
        verify(locationRepository, times(1)).findAllByUserId(1L);
    }

    @Test
    void testGetAllLocations_Success() {
        when(locationRepository.findAll()).thenReturn(Arrays.asList(location));
        when(modelMapper.map(location, LocationDTO.class)).thenReturn(locationDTO);

        List<LocationDTO> locations = locationService.getAllLocations();

        assertNotNull(locations);
        assertEquals(1, locations.size());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void testGetLocationById_Success() {
        when(locationRepository.findById(1L)).thenReturn(Optional.of(location));
        when(modelMapper.map(location, LocationReturnDTO.class)).thenReturn(new LocationReturnDTO());

        LocationReturnDTO result = locationService.getLocationById(1L);

        assertNotNull(result);
        verify(locationRepository, times(1)).findById(1L);
    }

    @Test
    void testGetLocationById_NotFound() {
        when(locationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(LocationNotFoundException.class, () -> locationService.getLocationById(1L));
        verify(locationRepository, times(1)).findById(1L);
    }
}


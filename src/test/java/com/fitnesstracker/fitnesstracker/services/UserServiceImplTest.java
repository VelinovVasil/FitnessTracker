package com.fitnesstracker.fitnesstracker.services;


import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.models.dto.UserDTO;
import com.fitnesstracker.fitnesstracker.models.entity.Role;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setRole(Role.USER);

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setRole("USER");
    }

    @Test
    public void getAllUsers_success() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        List<UserDTO> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUserById_success() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(modelMapper.map(any(User.class), eq(UserDTO.class))).thenReturn(userDTO);

        UserDTO result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void getUserById_notFound() {
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(1L);
        });

        assertEquals("User with such id not found", thrown.getMessage());
        verify(userRepository, times(1)).findById(1L);
    }
}


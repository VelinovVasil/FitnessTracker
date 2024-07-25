package com.fitnesstracker.fitnesstracker.services;


import com.fitnesstracker.fitnesstracker.config.JwtService;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserAlreadyExistsException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.models.entity.Role;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.models.request.AuthenticationRequest;
import com.fitnesstracker.fitnesstracker.models.request.RegisterRequest;
import com.fitnesstracker.fitnesstracker.models.response.AuthenticationResponse;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.impl.AuthenticationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister_Success() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");
        request.setPassword("password");

        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");

        when(userRepository.existsByEmailOrUsername(request.getEmail(), request.getUsername())).thenReturn(false);
        when(modelMapper.map(request, User.class)).thenReturn(user);
        when(encoder.encode(request.getPassword())).thenReturn("encodedpassword");
        when(userRepository.count()).thenReturn(1L);
        when(jwtService.generateToken(user, user.getId())).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.register(request);

        verify(userRepository, times(1)).saveAndFlush(user);
        assertEquals("jwtToken", response.getToken());
    }

    @Test
    public void testAuthenticate_Success() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        User user = new User();
        user.setUsername("testuser");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findUserByUsername(request.getUsername())).thenReturn(java.util.Optional.of(user));
        when(jwtService.generateToken(user, user.getId())).thenReturn("jwtToken");

        AuthenticationResponse response = authenticationService.authenticate(request);

        assertEquals("jwtToken", response.getToken());
    }

    @Test
    public void testAuthenticate_UserNotFound() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findUserByUsername(request.getUsername())).thenReturn(java.util.Optional.empty());

        try {
            authenticationService.authenticate(request);
        } catch (UserNotFoundException e) {
            assertEquals("No user with such username found", e.getMessage());
        }
    }

    @Test
    public void testRegister_UserAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setEmail("test@example.com");

        when(userRepository.existsByEmailOrUsername(request.getEmail(), request.getUsername())).thenReturn(true);

        try {
            authenticationService.register(request);
        } catch (UserAlreadyExistsException e) {
            assertEquals("User with this username / email already exists", e.getMessage());
        }
    }
}


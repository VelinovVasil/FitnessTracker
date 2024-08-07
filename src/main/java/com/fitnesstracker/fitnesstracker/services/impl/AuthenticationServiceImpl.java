package com.fitnesstracker.fitnesstracker.services.impl;

import com.fitnesstracker.fitnesstracker.config.JwtService;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserAlreadyExistsException;
import com.fitnesstracker.fitnesstracker.handler.exceptions.UserNotFoundException;
import com.fitnesstracker.fitnesstracker.models.entity.Role;
import com.fitnesstracker.fitnesstracker.models.entity.User;
import com.fitnesstracker.fitnesstracker.models.request.AuthenticationRequest;
import com.fitnesstracker.fitnesstracker.models.request.RegisterRequest;
import com.fitnesstracker.fitnesstracker.models.response.AuthenticationResponse;
import com.fitnesstracker.fitnesstracker.repositories.UserRepository;
import com.fitnesstracker.fitnesstracker.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder encoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        if (this.userRepository.existsByEmailOrUsername(request.getEmail(), request.getUsername())) {

            throw new UserAlreadyExistsException("User with this username / email already exists");

        }

        User user = this.modelMapper.map(request, User.class);
        user.setId(null);
        user.setPassword(this.encoder.encode(request.getPassword()));

        if (this.userRepository.count() == 0) {

            user.setRole(Role.ADMIN);

        } else {

            user.setRole(Role.USER);

        }

        this.userRepository.saveAndFlush(user);

        String jwtToken = this.jwtService.generateToken(user, user.getId());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        User user = this.userRepository.findUserByUsername(request.getUsername())
                .orElseThrow(() -> new UserNotFoundException("No user with such username found"));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        //User user = this.userRepository.findUserByUsername(request.getUsername())
        //        .orElseThrow(() -> new UserNotFoundException("No user with such username found"));

        String jwtToken = this.jwtService.generateToken(user, user.getId());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

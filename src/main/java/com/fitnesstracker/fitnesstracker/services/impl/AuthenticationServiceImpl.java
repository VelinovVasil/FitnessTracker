package com.fitnesstracker.fitnesstracker.services.impl;

import com.fitnesstracker.fitnesstracker.config.JwtService;
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

    @Autowired
    private PasswordEncoder encoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {

        User user = this.modelMapper.map(request, User.class);
        user.setPassword(this.encoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        this.userRepository.saveAndFlush(user);

        String jwtToken = this.jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = this.userRepository.findUserByUsername(request.getUsername())
                .orElseThrow();

        String jwtToken = this.jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}

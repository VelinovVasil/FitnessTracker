package com.fitnesstracker.fitnesstracker.services;

import com.fitnesstracker.fitnesstracker.models.request.AuthenticationRequest;
import com.fitnesstracker.fitnesstracker.models.request.RegisterRequest;
import com.fitnesstracker.fitnesstracker.models.response.AuthenticationResponse;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}

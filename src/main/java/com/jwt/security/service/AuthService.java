package com.jwt.security.service;

import com.jwt.security.dto.request.AuthenticationRequest;
import com.jwt.security.dto.response.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse login(AuthenticationRequest request);
    AuthenticationResponse refreshToken(String refreshToken);
    String logout(String token);
}

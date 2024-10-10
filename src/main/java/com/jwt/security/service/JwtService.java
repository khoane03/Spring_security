package com.jwt.security.service;

import com.jwt.security.entity.Roles;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    boolean validateToken(String token, UserDetails userDetails);

    String extractUsername(String token);

    Set<?> extractRoles(String token);

    String refreshToken(String token, UserDetails userDetails);

    void logout(String token, UserDetails userDetails);
}

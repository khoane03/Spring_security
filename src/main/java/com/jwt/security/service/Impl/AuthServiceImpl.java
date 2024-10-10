package com.jwt.security.service.Impl;

import com.jwt.security.dto.request.AuthenticationRequest;
import com.jwt.security.dto.response.AuthenticationResponse;
import com.jwt.security.repository.UserRepo;
import com.jwt.security.service.AuthService;
import com.jwt.security.service.JwtService;
import com.jwt.security.utils.exception.AppException;
import com.jwt.security.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    UserRepo userRepo;
    AuthenticationManager authenticationManager;
    JwtService jwtService;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var User = userRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(User))
                .build();
    }

    @Override
    public AuthenticationResponse refreshToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        var user = userRepo.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.TOKEN_INVALID));
        return AuthenticationResponse.builder()
                .token(jwtService.refreshToken(refreshToken, user))
                .build();
    }

    @Override
    public String logout(String token) {
        String username = jwtService.extractUsername(token);
        var user = userRepo.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.TOKEN_INVALID));
        jwtService.logout(token, user);
        return "Logout successful";
    }
}

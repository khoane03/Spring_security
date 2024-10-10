package com.jwt.security.controller;

import com.jwt.security.dto.request.AuthenticationRequest;
import com.jwt.security.dto.request.TokenRequest;
import com.jwt.security.dto.response.ApiResponse;
import com.jwt.security.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthenticationController {

    AuthService authenticationService;

    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody AuthenticationRequest request) {
        return ApiResponse.builder()
                .code(200)
                .message("Login successful")
                .data(authenticationService.login(request))
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<?> refreshToken(@Valid @RequestBody TokenRequest request) {
        String refreshToken = request.getRefreshToken();
        return ApiResponse.builder()
                .code(200)
                .message("Refresh successful")
                .data(authenticationService.refreshToken(refreshToken))
                .build();
    }

    @PostMapping("/logout")
    public ApiResponse<?> logout(@RequestBody TokenRequest token) {
        return ApiResponse.builder()
                .code(200)
                .data(authenticationService.logout(token.getRefreshToken()))
                .build();
    }

}

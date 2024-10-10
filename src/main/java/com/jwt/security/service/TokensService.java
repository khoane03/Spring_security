package com.jwt.security.service;

public interface TokensService {
    void saveOrUpdateToken(String token, String username);

    void deleteToken(String token);
}

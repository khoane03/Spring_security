package com.jwt.security.service.Impl;

import com.jwt.security.entity.Tokens;
import com.jwt.security.repository.TokensRepository;
import com.jwt.security.service.TokensService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class TokensServiceImpl implements TokensService {

    TokensRepository tokensRepository;

    @Override
    public void saveOrUpdateToken(String token, String username) {
        if(tokensRepository.existsByUsername(username)) {
            Tokens tokens = tokensRepository.findByUsername(username);
            tokens.setToken(token);
            tokensRepository.save(tokens);
        } else {
            Tokens tokens = Tokens.builder()
                    .token(token)
                    .username(username)
                    .build();
            tokensRepository.save(tokens);
        }
    }

    @Override
    public void deleteToken(String token) {

    }
}

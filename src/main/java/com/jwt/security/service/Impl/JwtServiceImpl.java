package com.jwt.security.service.Impl;

import com.jwt.security.entity.User;
import com.jwt.security.repository.TokensRepository;
import com.jwt.security.service.JwtService;
import com.jwt.security.service.TokensService;
import com.jwt.security.utils.exception.AppException;
import com.jwt.security.utils.exception.ErrorCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JwtServiceImpl implements JwtService {

    TokensRepository tokensRepository;
    TokensService tokensService;

    @Value("${security.jwt.signerKey}")
    @NonFinal
    String SECRET_KEY;

    @Value("${security.jwt.expiration-time}")
    @NonFinal
    long EXPIRATION_TIME;

    @Override
    public String generateToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) && !tokensRepository.existsByToken(token);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String refreshToken(String token, UserDetails userDetails) {
        if (!validateToken(token, userDetails)) {
            log.error("Token is invalid");
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        //Check if token exists in the database else update the token
        tokensService.saveOrUpdateToken(token, userDetails.getUsername());
        return buildToken(new HashMap<>(), userDetails);
    }

    @Override
    public void logout(String token, UserDetails userDetails) {
        if (!validateToken(token, userDetails)) {
            log.error("Invalid token");
            throw new AppException(ErrorCode.TOKEN_INVALID);
        }
        tokensService.saveOrUpdateToken(token, userDetails.getUsername());
    }

    @Override
    public Set<?> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("scope", Set.class);
    }

    Key getKey() {
        byte[] secret = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(secret);
    }


    String buildScope(User user) {
        StringBuilder scopes = new StringBuilder();
        user.getRoles().forEach(role -> scopes.append("ROLE_").append(role.getName()).append(" "));
        return scopes.toString().trim();
    }

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    String buildToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuer("http://localhost:8080")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("scope", buildScope((User) userDetails))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }


}

package com.jwt.security.repository;

import com.jwt.security.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TokensRepository extends JpaRepository<Tokens, Integer> {
    boolean existsByToken(String token);

    boolean existsByUsername(String username);

    Tokens findByUsername(String username);
}

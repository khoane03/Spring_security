package com.jwt.security.repository;

import com.jwt.security.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolesRepo extends JpaRepository<Roles, Integer> {
    boolean existsByName(String name);

    Roles findByName(String name);
}

package com.jwt.security.repository;

import com.jwt.security.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermissionRepo extends JpaRepository<Permission, Integer> {
    Permission findByName(String permission);

    boolean existsByName(String name);

    void deleteByName(String name);
}

package com.jwt.security.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_roles")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Roles extends BaseEntity {
    @Column(name = "name")
    String name;

    @ManyToMany
    @JoinTable(
            name = "tbl_role_permissions",
            joinColumns = @jakarta.persistence.JoinColumn(name = "role_id"),
            inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "permission_id")
    )
    Set<Permission> permissions;


    @ManyToMany(mappedBy = "roles")
    Set<User> users;
}

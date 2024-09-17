package com.jwt.security.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_users")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @Column(name = "full_name")
    String fullName;
    @Column(name = "username")
    String username;
    @Column(name = "password")
    String password;
    @Column(name = "dob")
    LocalDate dob;
    @Column(name = "email")
    String email;
    @Column(name = "phone")
    String phone;
    @Column(name = "status")
    String status;

    @ManyToMany
    @JoinTable(
            name = "tbl_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Roles> roles;

}

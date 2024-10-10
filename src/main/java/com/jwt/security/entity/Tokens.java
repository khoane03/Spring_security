package com.jwt.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_tokens")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Tokens extends BaseEntity {
    @Column(name = "access_token")
    String token;

    @Column(name = "user_name", unique = true)
    String username;
}

package com.jwt.security.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_permissions")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class Permission extends BaseEntity {
    @Column(name = "name")
    String name;
    @Column(name = "description")
    String description;
    @ManyToMany(mappedBy = "permissions")
    Set<Roles> roles;
}

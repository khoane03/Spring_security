package com.jwt.security.entity;

import com.jwt.security.utils.exception.AppException;
import com.jwt.security.utils.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_users")
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class User extends BaseEntity implements UserDetails {
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tbl_user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Roles> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(this.status == null || !this.status.equals("ACTIVE")){
           throw new AppException(ErrorCode.USER_NOT_ACTIVE);
        }
            return true;
    }
}

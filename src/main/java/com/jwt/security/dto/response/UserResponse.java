package com.jwt.security.dto.response;

import com.jwt.security.entity.Roles;
import com.jwt.security.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResponse {
    String fullName;
    String username;
    LocalDate dob;
    String email;
    String phone;
    String status;
    Set<String> roles;

    public UserResponse(User user) {
        if (user != null) {
            this.setFullName(user.getFullName());
            this.setUsername(user.getUsername());
            this.setDob(user.getDob());
            this.setEmail(user.getEmail());
            this.setPhone(user.getPhone());
            this.setStatus(user.getStatus());
            Set<String> roles = user.getRoles()
                    .stream()
                    .map(Roles::getName)
                    .collect(Collectors.toSet());
            this.setRoles(roles);
        }
    }
}

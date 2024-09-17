package com.jwt.security.dto.response;

import com.jwt.security.entity.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserResponse {
    String fullName;
    String username;
    String password;
    LocalDate dob;
    String email;
    String phone;
    String status;

    public UserResponse(User user) {
       if(user != null) {
           this.setFullName(user.getFullName());
           this.setUsername(user.getUsername());
           this.setPassword(user.getPassword());
           this.setDob(user.getDob());
           this.setEmail(user.getEmail());
           this.setPhone(user.getPhone());
           this.setStatus(user.getStatus());
       }
    }
}

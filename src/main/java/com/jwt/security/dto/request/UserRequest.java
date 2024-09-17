package com.jwt.security.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserRequest {
    @NotNull(message = "Name is required")
    String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 6, message = "Username must be at least 6 characters")
    String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one uppercase letter, one lowercase letter, and one number")
    String password;

//    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "Invalid date of birth")
    LocalDate dob;

    @Email(message = "Invalid email")
    String email;

    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
//    @Pattern(regexp = "^(0|[1-9][0-9]*)$", message = "Invalid phone number")
    String phone;

    String status;

}

package com.jwt.security.controller;


import com.jwt.security.dto.request.UserRequest;
import com.jwt.security.dto.response.ApiResponse;
import com.jwt.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user")
public class UserController {

    UserService userService;

    @PostMapping()
    public ApiResponse<?> saveUser(@Valid @RequestBody UserRequest userRequest) {
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("User saved successfully")
                .data(userService.saveUser(userRequest))
                .build();
    }

    @GetMapping()
    public ApiResponse<?> getAllUsers() {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("All users fetched successfully")
                .data(userService.getAllUsers())
                .build();
    }

    @PostMapping("/{id}")
    public ApiResponse<?> searchUserById(@PathVariable Integer id) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("User fetched successfully")
                .data(userService.searchUserById(id))
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteUserById(@PathVariable Integer id){
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Deletion of " + id +" successfully")
                .data(userService.deleteById(id))
                .build();
    }

}

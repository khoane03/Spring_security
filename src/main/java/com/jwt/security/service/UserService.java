package com.jwt.security.service;

import com.jwt.security.dto.request.UserRequest;
import com.jwt.security.dto.request.UserUpdateRequest;
import com.jwt.security.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse saveUser(UserRequest userRequest);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Integer id);

    UserResponse getMyInfo();

    UserResponse updateUserById(Integer id, UserUpdateRequest userRequest);

    boolean lockUserById(Integer id);

    UserResponse editStatusUser(String status, Integer id);

}

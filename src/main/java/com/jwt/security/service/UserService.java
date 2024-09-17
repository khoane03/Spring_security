package com.jwt.security.service;

import com.jwt.security.dto.request.UserRequest;
import com.jwt.security.dto.response.UserResponse;

import java.util.List;

public interface UserService {
  UserResponse saveUser(UserRequest userRequest);
  List<UserResponse> getAllUsers();
  UserResponse searchUserById(Integer id);
  UserResponse updateUserById(Integer id, UserRequest userRequest);
  boolean deleteById(Integer id);
  UserResponse editStatusUser(String status, Integer id);
}

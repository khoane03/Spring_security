package com.jwt.security.service.Impl;

import com.jwt.security.dto.request.UserRequest;
import com.jwt.security.dto.response.UserResponse;
import com.jwt.security.entity.User;
import com.jwt.security.repository.UserRepo;
import com.jwt.security.service.UserService;
import com.jwt.security.utils.exception.AppException;
import com.jwt.security.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepo userRepo;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse saveUser(UserRequest request) {
        if(userRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        return new UserResponse(userRepo.save(User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .email(request.getEmail())
                .phone(request.getPhone())
                .status("ACTIVE")
                .build()));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    @Override
    public UserResponse searchUserById(Integer id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            return new UserResponse(user.get());
        } else {
            throw new AppException(ErrorCode.ID_NOT_FOUND);
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        if(!userRepo.existsById(id)) {
            throw new AppException(ErrorCode.ID_NOT_FOUND);
        }
        userRepo.deleteById(id);
        return true;
    }

    @Override
    public UserResponse updateUserById(Integer id, UserRequest userRequest) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            User userUpdate = user.get();
            userUpdate.setFullName(userRequest.getFullName());
            userUpdate.setUsername(userRequest.getUsername());
            userUpdate.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userUpdate.setDob(userRequest.getDob());
            userUpdate.setEmail(userRequest.getEmail());
            userUpdate.setPhone(userRequest.getPhone());
            return new UserResponse(userRepo.save(userUpdate));
        }
        throw new AppException(ErrorCode.ID_NOT_FOUND);
    }

    @Override
    public UserResponse editStatusUser(String status, Integer id) {
        Optional<User> user = userRepo.findById(id);
        if(user.isPresent()){
            User userUpdate = user.get();
            userUpdate.setStatus(status);
            return new UserResponse(userRepo.save(userUpdate));
        }
        throw new AppException(ErrorCode.ID_NOT_FOUND);
    }
}

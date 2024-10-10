package com.jwt.security.service.Impl;

import com.jwt.security.dto.request.UserRequest;
import com.jwt.security.dto.request.UserUpdateRequest;
import com.jwt.security.dto.response.UserResponse;
import com.jwt.security.entity.Roles;
import com.jwt.security.entity.User;
import com.jwt.security.repository.RolesRepo;
import com.jwt.security.repository.UserRepo;
import com.jwt.security.service.UserService;
import com.jwt.security.utils.enums.RolesEnum;
import com.jwt.security.utils.exception.AppException;
import com.jwt.security.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepo userRepo;
    RolesRepo rolesRepo;
    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse saveUser(UserRequest request) {
        // Check if username is existed
        if (userRepo.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        //check if roles is not existed then create new role "USER"
        if (!rolesRepo.existsByName(RolesEnum.USER.name())) {
            Roles roles = new Roles();
            roles.setName(RolesEnum.USER.name());
            rolesRepo.save(roles);
        }
        return new UserResponse(userRepo.save(User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .dob(request.getDob())
                .email(request.getEmail())
                .phone(request.getPhone())
                .status("ACTIVE")
                .roles(new HashSet<>(Set.of(rolesRepo.findByName(RolesEnum.USER.name()))))
                .build()));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name")
    public UserResponse getUserById(Integer id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            return new UserResponse(user.get());
        } else {
            throw new AppException(ErrorCode.ID_NOT_FOUND);
        }
    }

    @Override
    public UserResponse getMyInfo() {
        // Get current user in security context
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return new UserResponse(userRepo.findByUsername(username).isPresent() ? userRepo.findByUsername(username).get() : null);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public boolean lockUserById(Integer id) {
        userRepo.findById(id).ifPresentOrElse(user -> {
            user.setStatus("BLOCKED");
            userRepo.save(user);
        }, () -> {
            throw new AppException(ErrorCode.ID_NOT_FOUND);
        });
        return true;
    }

    @Override
    @PostAuthorize("returnObject.username == authentication.name OR hasRole('ADMIN')")
//    @PreAuthorize("#id == authentication.principal.id OR hasRole('ADMIN')")
    public UserResponse updateUserById(Integer id, UserUpdateRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info(authentication.getPrincipal().toString());

        User updatedUser = userRepo.findByUsername(authentication.getName()).map(userUpdate -> {
            userUpdate.setFullName(userRequest.getFullName());
            userUpdate.setPassword(passwordEncoder.encode(userRequest.getPassword()));
            userUpdate.setDob(userRequest.getDob());
            userUpdate.setEmail(userRequest.getEmail());
            userUpdate.setPhone(userRequest.getPhone());
            return userRepo.save(userUpdate);
        }).orElseThrow(() -> new AppException(ErrorCode.ID_NOT_FOUND));
        return new UserResponse(updatedUser);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse editStatusUser(String status, Integer id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            User userUpdate = user.get();
            userUpdate.setStatus(status);
            return new UserResponse(userRepo.save(userUpdate));
        }
        throw new AppException(ErrorCode.ID_NOT_FOUND);
    }
}

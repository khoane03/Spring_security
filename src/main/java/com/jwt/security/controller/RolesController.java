package com.jwt.security.controller;

import com.jwt.security.dto.request.RolesRequest;
import com.jwt.security.dto.response.ApiResponse;
import com.jwt.security.service.RolesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/roles")
public class RolesController {

    RolesService rolesService;

    @PostMapping()
    public ApiResponse<?> createRole(@Valid @RequestBody RolesRequest rolesRequest) {
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Role created successfully")
                .data(rolesService.createRole(rolesRequest))
                .build();
    }

    @GetMapping()
    public ApiResponse<?> getAllRoles() {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("All roles fetched successfully")
                .data(rolesService.getAll())
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponse<?> getRoleByName(@PathVariable String name) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Deletion of role successfully")
                .data(rolesService.deleteRole(name))
                .build();
    }
}

package com.jwt.security.controller;

import com.jwt.security.dto.request.PermissionRequest;
import com.jwt.security.dto.response.ApiResponse;
import com.jwt.security.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/permission")
public class PermissionController {

    PermissionService permissionService;

    @PostMapping()
    public ApiResponse<?> savePermission(@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.builder()
                .code(HttpStatus.CREATED.value())
                .message("Permission saved successfully")
                .data(permissionService.createPermission(permissionRequest))
                .build();
    }

    @GetMapping()
    public ApiResponse<?> getAllPermissions() {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("All permissions fetched successfully")
                .data(permissionService.getAllPermissions())
                .build();
    }

    @DeleteMapping("/{name}")
    public ApiResponse<?> deletePermissionById(@PathVariable String name) {
        return ApiResponse.builder()
                .code(HttpStatus.OK.value())
                .message("Deletion of permission successfully")
                .data(permissionService.deleteById(name))
                .build();
    }
}

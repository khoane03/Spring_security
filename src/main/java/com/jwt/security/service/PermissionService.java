package com.jwt.security.service;

import com.jwt.security.dto.request.PermissionRequest;
import com.jwt.security.dto.response.PermissionResponse;


import java.util.List;

public interface PermissionService {
    PermissionResponse createPermission(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    boolean deleteById(String name);
}

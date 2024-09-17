package com.jwt.security.service.Impl;

import com.jwt.security.dto.request.PermissionRequest;
import com.jwt.security.dto.response.PermissionResponse;
import com.jwt.security.entity.Permission;
import com.jwt.security.repository.PermissionRepo;
import com.jwt.security.service.PermissionService;
import com.jwt.security.utils.exception.AppException;
import com.jwt.security.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class PermissionImplService implements PermissionService {

    PermissionRepo permissionRepo;

    @Override
    public PermissionResponse createPermission(PermissionRequest request) {
        if (permissionRepo.existsByName(request.getName())) {
            throw new AppException(ErrorCode.PERMISSION_EXISTED);
        }
        return new PermissionResponse(permissionRepo.save(Permission.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build()));
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepo.findAll()
                .stream()
                .map(PermissionResponse::new)
                .toList();
    }

    @Override
    public boolean deleteById(String name) {
        if (!permissionRepo.existsByName(name)) {
            throw new AppException(ErrorCode.NAME_NOT_FOUND);
        }
        permissionRepo.deleteByName(name);
        return true;
    }
}

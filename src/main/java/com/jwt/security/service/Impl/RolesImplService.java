package com.jwt.security.service.Impl;

import com.jwt.security.dto.request.RolesRequest;
import com.jwt.security.dto.response.RolesResponse;
import com.jwt.security.entity.Permission;
import com.jwt.security.entity.Roles;
import com.jwt.security.repository.PermissionRepo;
import com.jwt.security.repository.RolesRepo;
import com.jwt.security.service.RolesService;
import com.jwt.security.utils.exception.AppException;
import com.jwt.security.utils.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RolesImplService implements RolesService {

    RolesRepo rolesRepo;
    PermissionRepo permissionRepo;

    @Override
    public RolesResponse createRole(RolesRequest request) {
        if (rolesRepo.existsByName(request.getName())) {
            throw new AppException(ErrorCode.ROLE_EXISTED);
        }
        Roles role = new Roles();
        role.setName(request.getName());
        Set<Permission> permissions = new HashSet<>();
        for (String permission : request.getPermissions()) {
            permissions.add(permissionRepo.findByName(permission));
        }
        role.setPermissions(permissions);
        return new RolesResponse(rolesRepo.save(role));
    }

    @Override
    public List<RolesResponse> getAll() {
        return rolesRepo.findAll()
                .stream()
                .map(RolesResponse::new)
                .toList();
    }

    @Override
    public boolean deleteRole(String name) {
        if (!rolesRepo.existsByName(name)) {
            throw new AppException(ErrorCode.NAME_NOT_FOUND);
        }
        return rolesRepo.existsByName(name);
    }
}

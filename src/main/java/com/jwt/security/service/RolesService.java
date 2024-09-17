package com.jwt.security.service;

import com.jwt.security.dto.request.RolesRequest;
import com.jwt.security.dto.response.RolesResponse;

import java.util.List;

public interface RolesService {
    RolesResponse createRole(RolesRequest request);
    List<RolesResponse> getAll();
    boolean deleteRole(String name);

}

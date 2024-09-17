package com.jwt.security.dto.response;

import com.jwt.security.entity.Permission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class PermissionResponse {
    String name;
    String description;

    public PermissionResponse(Permission permission) {
        if (permission != null) {
            this.setName(permission.getName());
            this.setDescription(permission.getDescription());
        }

    }
}

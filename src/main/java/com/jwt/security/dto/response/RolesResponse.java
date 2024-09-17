package com.jwt.security.dto.response;

import com.jwt.security.entity.Permission;
import com.jwt.security.entity.Roles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class RolesResponse {
    String name;
    Set<String> permissions;

    public RolesResponse(Roles roles) {
        if (roles != null) {
            this.setName(roles.getName());
            if(roles.getPermissions() != null){
                Set<String> permissions = new HashSet<>();
                for (Permission permission : roles.getPermissions()) {
                    permissions.add(permission.getName());
                }
                this.setPermissions(permissions);
            }
        }
    }


}

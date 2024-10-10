package com.jwt.security.configuration;

import com.jwt.security.entity.Roles;
import com.jwt.security.entity.User;
import com.jwt.security.repository.RolesRepo;
import com.jwt.security.repository.UserRepo;
import com.jwt.security.utils.enums.RolesEnum;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AppInitConfigs {
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepo userRepo, RolesRepo rolesRepo) {  //always run this code when the application starts
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                if (!rolesRepo.existsByName(RolesEnum.ADMIN.name())) {
                    Roles roles = new Roles();
                    roles.setName(RolesEnum.ADMIN.name());
                    rolesRepo.save(roles);
                }
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(new HashSet<>(Set.of(rolesRepo.findByName(RolesEnum.ADMIN.name())
                        )))
                        .status("ACTIVE")
                        .build();
                userRepo.save(user);
                log.warn("The default administrator account has been created: User Name : admin, Password : admin");
                log.warn("Please change the password after logging in for the first time");
            }
        };
    }
}

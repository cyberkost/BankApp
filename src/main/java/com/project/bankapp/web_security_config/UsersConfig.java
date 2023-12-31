package com.project.bankapp.web_security_config;

import com.project.bankapp.entity.enums.Roles;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

/**
 * Configuration class for setting up initial users and authorities in the application.
 */
@Configuration
@RequiredArgsConstructor
public class UsersConfig {
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.username}")
    private String adminUsername;
    @Value("${admin.password}")
    private String adminPassword;

    /**
     * Creates initial users and assigns roles if they don't already exist.
     * The method is invoked after the bean initialization.
     */
    @PostConstruct
    public void createUsersAndAuthorities() {
        if (!userDetailsManager.userExists(adminUsername)) {
            String encodedPassword = passwordEncoder.encode(adminPassword);
            userDetailsManager.createUser(User.withUsername(adminUsername)
                    .password(encodedPassword)
                    .roles(Roles.ADMIN.toString(), Roles.MANAGER.toString(), Roles.USER.toString())
                    .build());
        }
    }
}

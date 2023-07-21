package com.project.bankapp.web_security_config;

import com.project.bankapp.entity.UserSecurity;
import com.project.bankapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

import static com.project.bankapp.entity.enums.SecurityRoles.ADMIN;
import static com.project.bankapp.entity.enums.SecurityRoles.USER;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    @Autowired
    private UserRepository userRepository;

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .requestMatchers("/main").hasRole(String.valueOf(ADMIN))
//                .anyRequest().permitAll()
//                .and()
//                .formLogin();
//        return http.build();
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        List<UserSecurity> userSecurities = userRepository.findAll();
        List<UserDetails> users = new ArrayList<>();
        for (UserSecurity userSecurity : userSecurities) {
            UserDetails userDetails = User
                    .withUsername(userSecurity.getUsername())
                    .password(String.valueOf(userSecurity.getId()))
                    .roles(ADMIN.toString())
                    .build();
            users.add(userDetails);
        }

        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}

package com.project.bankapp.web_security_config;

import com.project.bankapp.entity.enums.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Configuration class for setting up web security and access control rules for endpoints.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    /**
     * Creates a {@link JdbcUserDetailsManager} instance for managing user details from a JDBC data source.
     *
     * @param dataSource The JDBC data source.
     * @return A JdbcUserDetailsManager instance.
     */
    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * Creates a {@link BCryptPasswordEncoder} instance for encoding passwords.
     *
     * @return A BCryptPasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures security filter chain and access control rules for various endpoints.
     *
     * @param http The HttpSecurity instance to configure.
     * @return A SecurityFilterChain instance.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/v3/api-docs/**").permitAll();
                    auth.requestMatchers("/swagger-ui/**").permitAll();
                    auth.requestMatchers("/swagger-ui.html").permitAll();

                    auth.requestMatchers("/registration/new-client").permitAll();

                    auth.requestMatchers("/client/find/{uuid}").hasRole(Roles.USER.name());
                    auth.requestMatchers("/client/update/{uuid}").hasRole(Roles.USER.name());
                    auth.requestMatchers("/account/create/with-client-id/{uuid}").hasRole(Roles.USER.name());
                    auth.requestMatchers("/transaction/create").hasRole(Roles.USER.name());
                    auth.requestMatchers("/transaction/transfer/").hasRole(Roles.USER.name());

                    auth.requestMatchers("/account/**").hasRole(Roles.MANAGER.name());
                    auth.requestMatchers("/client/**").hasRole(Roles.MANAGER.name());
                    auth.requestMatchers("/transaction/**").hasRole(Roles.MANAGER.name());
                    auth.requestMatchers("/manager/find/{uuid}").hasRole(Roles.MANAGER.name());

                    auth.requestMatchers("/**").hasRole(Roles.ADMIN.name());

                })
                .formLogin(formLogin -> formLogin.defaultSuccessUrl("/swagger-ui.html"))
                .logout(Customizer.withDefaults())
                .build();
    }
}

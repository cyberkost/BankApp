package com.project.bankapp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.project.bankapp.entity.UserSecurity;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository<UserSecurity, Integer> {
    UserSecurity findByUsername(String username);
}

package com.project.bankapp.entity;

import com.project.bankapp.entity.enums.SecurityRoles;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_security")
public class UserSecurity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column
    private String username;

    @Enumerated(EnumType.STRING)
    @Column
    private SecurityRoles securityRoles;
}

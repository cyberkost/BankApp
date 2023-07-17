package com.project.bankapp.entity;

//import com.project.bankapp.entity.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;
//@Data
//@Entity
//@Table(name = "user")
//public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "uuid")
//    private UUID uuid;
//    private String username;
//    private String password;
//    private boolean active;
//
//    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
//    @Enumerated(EnumType.STRING)
//    private Set<Role> roles;
//
//}

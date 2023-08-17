package com.project.bankapp.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entity class representing a user authority in the system.
 * An authority defines a specific permission or role that a user can possess.
 */
@Data
@Entity
@Table(name = "authorities")
public class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authority;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;
}

package com.project.bankapp.entity;

import com.project.bankapp.entity.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "manager_uuid")
    private UUID managerUuid;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "age", length = 50)
    private String age;

    @Column(name = "citizenship", length = 100)
    private String citizenship;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 100)
    private String address;

    @Column(name = "phone", length = 100)
    private String phone;

    @Column(name = "tax_code", length = 50)
    private String taxCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ClientStatus status;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
}
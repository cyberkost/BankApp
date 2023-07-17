package com.project.bankapp.entity;

import com.project.bankapp.entity.enums.ClientStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "client_uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "manager_uuid")
    private Manager manager;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Account> accounts;

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

    public Client() {

    }

    public Client(String firstName, String lastName, String age, String citizenship, String email, String address, String phone, ClientStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.citizenship = citizenship;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.status = status;
    }
}
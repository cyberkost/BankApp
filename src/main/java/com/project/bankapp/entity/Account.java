package com.project.bankapp.entity;

import com.project.bankapp.entity.enums.AccountStatus;
import com.project.bankapp.entity.enums.AccountType;
import com.project.bankapp.entity.enums.CurrencyCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "account_uuid")
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "client_uuid")
    private Client client;

    @OneToMany(mappedBy = "debitAccount", cascade = CascadeType.ALL)
    private List<Transaction> debitTransactions;

    @OneToMany(mappedBy = "creditAccount", cascade = CascadeType.ALL)
    private List<Transaction> creditTransactions;

    @Column(name = "name", length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private AccountType type;

    @Column(name = "balance", precision = 15, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_code")
    private CurrencyCode currencyCode;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @Column(name = "is_deleted", columnDefinition = "boolean default false")
    private boolean isDeleted;
}
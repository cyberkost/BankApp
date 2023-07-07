package com.project.bankapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "type")
    private byte type;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Foreign Key
//    @JoinColumn(name = "debit_account_id")
//    private UUID debitAccountId;

    // Foreign Key
//    @JoinColumn(name = "credit_account_id")
//    private UUID creditAccountId;
}
package com.project.bankapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "agreement")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Agreement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "interest_rate")
    private Double interestRate;

    @Column(name = "status")
    private String status;

    @Column(name = "sum")
    private Double sum;

    @Column(name = "created_at")
    private LocalDateTime created;

    @Column(name = "updated_at")
    private LocalDateTime updated;

//    @JoinColumn(name = "product_id")
//    private UUID productId;

    // Foreign Key
//    @JoinColumn(name = "account_id")
//    private UUID accountId;
}
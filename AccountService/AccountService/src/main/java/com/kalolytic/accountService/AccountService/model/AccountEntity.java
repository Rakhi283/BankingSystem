package com.kalolytic.accountService.AccountService.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String accountNo;

    @Column(nullable = false)
    private UUID custId;

//    private AccountType accountType;

    private BigDecimal balance;

//    private AccountStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
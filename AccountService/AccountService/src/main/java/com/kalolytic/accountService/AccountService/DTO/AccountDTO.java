package com.kalolytic.accountService.AccountService.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountDTO {
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

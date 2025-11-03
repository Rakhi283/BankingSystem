package com.kalolytic.accountService.AccountService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Helps generate incremental ID
    private UUID id; // internal primary key, not shown to user

    private UUID accountId; // external unique id (optional)
    private UUID customerId;
    private String accountNumber; // <- store generated number here
    private String accountType;
    private Double balance;
    private String branchCode;
    private LocalDateTime createdAt;
    private String customerName;
    private String customerEmail;

    public void setBalance(Double balance){
        if(balance == null)
            balance = 0.0;
        this.balance = balance;
    }
}

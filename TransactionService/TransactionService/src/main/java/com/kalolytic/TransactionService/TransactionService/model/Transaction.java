package com.kalolytic.TransactionService.TransactionService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transId;
    private String fromAccountNo;
    private String toAccountNo;
    private BigDecimal amount;
    private UUID customerId;

//    @Enumerated(EnumType.STRING)
//    private TransactionType type;

    private Instant timestamp;
//    private TransactionStatus status;

}

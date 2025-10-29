package com.kalolytic.TransactionService.TransactionService.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID transId;
    private String fromAccountNo;
    private String toAccountNo;

    private BigDecimal amount;

//    @Enumerated(EnumType.STRING)
//    private TransactionType type;

    private Instant timestamp;
//    private TransactionStatus status;
}

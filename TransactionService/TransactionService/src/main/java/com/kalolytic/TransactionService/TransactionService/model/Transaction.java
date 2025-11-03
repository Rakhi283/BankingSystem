package com.kalolytic.TransactionService.TransactionService.model;

import com.kalolytic.commonModel.CommonModel.enums.TransactionStatus;
import com.kalolytic.commonModel.CommonModel.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID customerId;
    private String fromAccountNo;
    private String toAccountNo;
    private Double amount;
    private String remarks;
    private TransactionType type; // TRANSFER, DEBIT, CREDIT
    private TransactionStatus status; // PENDING, SUCCESS, FAILED, REVERSED
    private LocalDateTime timestamp;
    private String failureReason;
}

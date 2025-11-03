package com.kalolytic.commonModel.CommonModel.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kalolytic.commonModel.CommonModel.enums.TransactionStatus;
import com.kalolytic.commonModel.CommonModel.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private UUID id;
    private UUID customerId;
    @NotBlank
    private String fromAccountNo;
    @NotBlank
    private String toAccountNo;
    private Double amount;
    private String remarks;
    private String type;
    private String status;
    private LocalDateTime timestamp;
    private String failureReason;
}


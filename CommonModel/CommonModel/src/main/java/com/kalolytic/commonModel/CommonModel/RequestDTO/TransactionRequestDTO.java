package com.kalolytic.commonModel.CommonModel.RequestDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class TransactionRequestDTO {
    @NotBlank
    private String fromAccountNo;
    @NotBlank
    private String toAccountNo;
    private Double amount;
    private UUID customerId;
    private String remarks;
}

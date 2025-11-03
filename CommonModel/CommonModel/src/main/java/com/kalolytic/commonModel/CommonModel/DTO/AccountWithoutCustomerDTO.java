package com.kalolytic.commonModel.CommonModel.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountWithoutCustomerDTO {
    private UUID id;
    private String accountNumber;
   private UUID accountId;
    private UUID customerId;
    private Double balance;
    @NotBlank
private String branchCode;
    @NotBlank
private String accountType;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}

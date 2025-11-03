package com.kalolytic.commonModel.CommonModel.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AccountDTO {
    private UUID id;
    private String accountNumber;
private UUID accountId;
    private UUID customerId;
@NotBlank
private String branchCode;
@NotBlank
private String accountType;

    private Double balance;
@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    private String customerName;
    private String customerEmail;

}


package com.kalolytic.commonModel.CommonModel.RequestDTO;


import lombok.Data;

@Data
public class AccountBalanceChangeRequest {
    private String accountNumber;
    private Double amount;
}


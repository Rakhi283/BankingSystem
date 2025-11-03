package com.kalolytic.commonModel.CommonModel.DTO;

import lombok.Data;

@Data
public class AccountUpdateDTO {

    private String accountType;
    private String customerName;
    private String customerEmail;
    private Double balance;
}

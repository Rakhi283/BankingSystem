package com.kalolytic.accountService.AccountService.utility;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AccountNumberGenerator {

    private static final String BANK_CODE = "91";

    public String generate(String branchCode, UUID id) {
        String numericId = id.toString().replaceAll("[^0-9]", "");
        numericId = numericId.length() > 8 ? numericId.substring(0, 8) : String.format("%08d", Integer.parseInt(numericId));
        return BANK_CODE + branchCode.replaceAll("[^0-9A-Za-z]", "").toUpperCase() + numericId;
    }
}



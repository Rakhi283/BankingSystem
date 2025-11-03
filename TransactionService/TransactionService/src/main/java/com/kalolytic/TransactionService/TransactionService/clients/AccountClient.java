package com.kalolytic.TransactionService.TransactionService.clients;

import com.kalolytic.TransactionService.TransactionService.config.FeignClientConfig;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountBalanceChangeRequest;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountVerificationRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name="ACCOUNTSERVICE", configuration = FeignClientConfig.class)
public interface AccountClient {

    @PostMapping("/account/debit")
    AccountBalanceChangeRequest debitAmount(AccountBalanceChangeRequest request);


    @PostMapping("/account/credit")
    AccountBalanceChangeRequest creditAmount(AccountBalanceChangeRequest request);
}

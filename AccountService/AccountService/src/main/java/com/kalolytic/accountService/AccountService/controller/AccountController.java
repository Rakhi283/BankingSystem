package com.kalolytic.accountService.AccountService.controller;

import com.kalolytic.accountService.AccountService.Service.AccountService;
import com.kalolytic.commonModel.CommonModel.DTO.AccountDTO;
import com.kalolytic.commonModel.CommonModel.DTO.AccountUpdateDTO;
import com.kalolytic.commonModel.CommonModel.DTO.AccountWithoutCustomerDTO;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountVerificationRequest;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountBalanceChangeRequest;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/account")
public class AccountController {

    private static final Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ResponseStructure<AccountDTO>> create(@Valid @RequestBody AccountDTO dto) {
        log.info("Creating account: {}", dto);
        AccountDTO saved = accountService.create(dto);
        log.info("Account created with ID: {}", saved.getAccountId());

        ResponseStructure<AccountDTO> response = new ResponseStructure<>();
        response.setData(saved);
        response.setMessage("Account created successfully!");
        response.setStatus("SUCCESS");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<AccountWithoutCustomerDTO>> getById(@PathVariable UUID id) {
        log.info("Fetching account with ID: {}", id);
        AccountWithoutCustomerDTO dto = accountService.getById(id);
        log.info("Fetched account: {}", dto);

        ResponseStructure<AccountWithoutCustomerDTO> response = new ResponseStructure<>();
        response.setData(dto);
        response.setMessage("Account fetched successfully!");
        response.setStatus("SUCCESS");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseStructure<List<AccountWithoutCustomerDTO>>> getAllAccounts() {
        log.info("Fetching accounts.. ");
        List<AccountWithoutCustomerDTO> dto = accountService.getAllAccounts();
        log.info("Fetched accounts: {}", dto);

        ResponseStructure<List<AccountWithoutCustomerDTO>> response = new ResponseStructure<>();
        response.setData(dto);
        response.setMessage("Accounts fetched successfully!");
        response.setStatus("SUCCESS");

        return ResponseEntity.ok(response);
    }



    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ResponseStructure<List<AccountDTO>>> getByCustomer(@PathVariable UUID customerId) {
        log.info("Fetching... accounts for customer ID: {}", customerId);
        List<AccountDTO> accounts = accountService.getByCustomer(customerId);
        log.info("Fetched {} accounts for customer {}", accounts.size(), customerId);

        ResponseStructure<List<AccountDTO>> response = new ResponseStructure<>();
        response.setData(accounts);
        response.setMessage("All Accounts fetched successfully!");
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseStructure<AccountUpdateDTO>> updateAccount(@PathVariable UUID id, @Valid @RequestBody AccountUpdateDTO dto) {
        log.info("Updating account ID: {} with data: {}", id, dto);
        AccountUpdateDTO updated = accountService.updateAccount(id, dto);
        log.info("Account updated: {}", updated);

        ResponseStructure<AccountUpdateDTO> response = new ResponseStructure<>();
        response.setData(updated);
        response.setMessage("Account updated successfully!");
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ResponseStructure<String>> delete(@PathVariable UUID id) {
        log.warn("Deleting account with ID: {}", id);
        String message = accountService.deleteAccount(id);
        log.info("Delete Result: {}", message);

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setData(null);
        response.setMessage(message);
        response.setStatus("SUCCESS");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/check/verify")
    public ResponseEntity<ResponseStructure<Boolean>> accountBelongsToCustomer(
            @Valid @RequestBody AccountVerificationRequest request) {

        log.info("Verifying account {} belongs to customer {}", request.getAccountId(), request.getCustomerId());
        boolean belongs = accountService.accountBelongsToCustomer(request);
        log.info("Verification result: {}", belongs);

        ResponseStructure<Boolean> response = new ResponseStructure<>();
            response.setData(belongs);
        if (belongs) {
            response.setMessage("Account belongs to the given customer.");
            response.setStatus("SUCCESS");
            return ResponseEntity.ok(response);
        } else {
            response.setMessage("Account does not belongs to the given customer.");
            response.setStatus("FAILURE");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/credit")
    public ResponseEntity<ResponseStructure<AccountBalanceChangeRequest>> creditAmount(
         @Valid @RequestBody AccountBalanceChangeRequest transaction
    ) {
        log.info("Credit API called for account: {} with amount: {}", transaction.getAmount(),transaction.getAccountNumber());
        AccountBalanceChangeRequest credited = accountService.creditAmount(transaction);
        ResponseStructure<AccountBalanceChangeRequest>  response = new ResponseStructure<>("Amount credited successfully!","SUCCESS",credited);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit")
    public ResponseEntity<ResponseStructure<AccountBalanceChangeRequest>> debitAmount(
            @Valid @RequestBody AccountBalanceChangeRequest transaction
    ) {
        log.info("Debit API called for account: {} with amount: {}", transaction.getAmount(),transaction.getAccountNumber());
        AccountBalanceChangeRequest Debited = accountService.DebitAmount(transaction);
        ResponseStructure<AccountBalanceChangeRequest>  response = new ResponseStructure<>("Amount Debited successfully!","SUCCESS",Debited);
        return ResponseEntity.ok(response);
    }
}
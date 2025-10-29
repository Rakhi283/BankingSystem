package com.kalolytic.accountService.AccountService.controller;

import com.kalolytic.accountService.AccountService.DTO.AccountDTO;
import com.kalolytic.accountService.AccountService.Service.AccountService;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<ResponseStructure<AccountDTO>> create(@RequestBody AccountDTO dto) {
        AccountDTO saved = accountService.create(dto);
        ResponseStructure<AccountDTO> response = new ResponseStructure<>(
                "Account created successfully!", HttpStatus.CREATED.toString(), saved
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<AccountDTO>> getById(@PathVariable UUID id) {
        AccountDTO dto = accountService.getById(id);
        ResponseStructure<AccountDTO> response = new ResponseStructure<>(
                "Account fetched successfully!", HttpStatus.OK.toString(), dto
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ResponseStructure<List<AccountDTO>>> getByCustomer(@PathVariable UUID customerId) {
        List<AccountDTO> accounts = accountService.getByCustomer(customerId);
        ResponseStructure<List<AccountDTO>> response = new ResponseStructure<>(
                "Accounts fetched successfully for customer: " + customerId,
                HttpStatus.OK.toString(),
                accounts
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseStructure<AccountDTO>> updateAccount(@PathVariable UUID id, @RequestBody AccountDTO dto) {
        AccountDTO updated = accountService.updateAccount(id, dto);
        ResponseStructure<AccountDTO> response = new ResponseStructure<>(
                "Account updated successfully!",
                HttpStatus.OK.toString(),
                updated
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> delete(@PathVariable UUID id) {
        String message = accountService.deleteAccount(id);
        ResponseStructure<String> response = new ResponseStructure<>(
                message,
                HttpStatus.OK.toString(),
                null
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/customer/{customerId}")
    public ResponseEntity<ResponseStructure<Boolean>> accountBelongsToCustomer(
            @PathVariable("accountId") UUID accountId,
            @PathVariable("customerId") UUID customerId) {


        boolean belongs = accountService.accountBelongsToCustomer(accountId, customerId);
        ResponseStructure<Boolean> response = new ResponseStructure<>(
                belongs
                        ? "Account belongs to the given customer."
                        : "Account does not belong to the given customer.",
                HttpStatus.OK.toString(),
                belongs
        );
        return ResponseEntity.ok(response);

    }
}
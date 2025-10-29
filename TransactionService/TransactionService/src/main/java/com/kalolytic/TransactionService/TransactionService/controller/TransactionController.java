package com.kalolytic.TransactionService.TransactionService.controller;

import com.kalolytic.TransactionService.TransactionService.DTO.TransactionDTO;
import com.kalolytic.TransactionService.TransactionService.service.TransactionService;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ResponseStructure<TransactionDTO>> createTransaction(@RequestBody TransactionDTO dto) {
        TransactionDTO created = transactionService.createTransaction(dto);
        ResponseStructure<TransactionDTO> response = new ResponseStructure<>(
                "Transaction created successfully", HttpStatus.CREATED.toString(), created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<TransactionDTO>> getTransactionById(@PathVariable UUID id) {
        TransactionDTO dto = transactionService.getTransactionById(id);
        ResponseStructure<TransactionDTO> response = new ResponseStructure<>(
                "Transaction fetched successfully", HttpStatus.OK.toString(), dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/account/{accountNo}")
    public ResponseEntity<ResponseStructure<List<TransactionDTO>>> getTransactionsByAccount(@PathVariable String accountNo) {
        List<TransactionDTO> list = transactionService.getTransactionsByAccountNo(accountNo);
        ResponseStructure<List<TransactionDTO>> response = new ResponseStructure<>(
                "Transactions fetched successfully", HttpStatus.OK.toString(), list);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<TransactionDTO>>> getAllTransactions() {
        List<TransactionDTO> list = transactionService.getAllTransactions();
        ResponseStructure<List<TransactionDTO>> response = new ResponseStructure<>(
                "All transactions fetched successfully", HttpStatus.OK.toString(), list);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customer/{customerId}/account/{accountNo}")
    public ResponseEntity<ResponseStructure<List<TransactionDTO>>> getTransactionsByCustomerAndAccount(
            @PathVariable UUID customerId,
            @PathVariable String accountNo) {

        List<TransactionDTO> list = transactionService.getTransactionsByCustomerAndAccount(customerId, accountNo);
        ResponseStructure<List<TransactionDTO>> response = new ResponseStructure<>(
                "Transactions fetched successfully", HttpStatus.OK.toString(), list);
        return ResponseEntity.ok(response);
    }
}

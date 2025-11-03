package com.kalolytic.TransactionService.TransactionService.controller;


import com.kalolytic.TransactionService.TransactionService.service.TransactionService;
import com.kalolytic.commonModel.CommonModel.DTO.TransactionDTO;
import com.kalolytic.commonModel.CommonModel.RequestDTO.TransactionRequestDTO;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping("/transfer")
    public ResponseEntity<ResponseStructure<TransactionDTO>> transfer(@Valid @RequestBody TransactionRequestDTO request) {
        log.info("Transfer API called: {}", request);
        TransactionDTO dto = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseStructure<>("Transaction completed", "SUCCESS", dto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<TransactionDTO>> getById(@PathVariable UUID id){
        TransactionDTO dto = transactionService.getTransactionById(id);
        return ResponseEntity.ok(new ResponseStructure<>("Transaction fetched", "SUCCESS", dto));
    }


    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<ResponseStructure<List<TransactionDTO>>> getByAccount(@PathVariable String accountNumber){
        List<TransactionDTO> list = transactionService.getTransactionsByAccount(accountNumber);
        return ResponseEntity.ok(new ResponseStructure<>("Transactions fetched", "SUCCESS", list));
    }


    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ResponseStructure<List<TransactionDTO>>> getByCustomer(@PathVariable UUID customerId){
        List<TransactionDTO> list = transactionService.getTransactionsByCustomer(customerId);
        return ResponseEntity.ok(new ResponseStructure<>("Transactions fetched", "SUCCESS", list));
    }


    @GetMapping("/get/all")
    public ResponseEntity<ResponseStructure<List<TransactionDTO>>> getAll(){
        List<TransactionDTO> list = transactionService.getAllTransactions();
        return ResponseEntity.ok(new ResponseStructure<>("All transactions", "SUCCESS", list));
    }


    @PostMapping("/{id}/reverse")
    public ResponseEntity<ResponseStructure<TransactionDTO>> reverse(@PathVariable UUID id, @RequestParam(required = false) String reason){
        TransactionDTO dto = transactionService.reverseTransaction(id, reason);
        return ResponseEntity.ok(new ResponseStructure<>("Transaction reversed", "SUCCESS", dto));
    }
}

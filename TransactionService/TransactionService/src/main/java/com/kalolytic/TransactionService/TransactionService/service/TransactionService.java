package com.kalolytic.TransactionService.TransactionService.service;

import com.kalolytic.commonModel.CommonModel.DTO.TransactionDTO;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountVerificationRequest;
import com.kalolytic.commonModel.CommonModel.RequestDTO.TransactionRequestDTO;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionService {
    TransactionDTO createTransaction(TransactionRequestDTO request);
    TransactionDTO getTransactionById(UUID id);
    List<TransactionDTO> getTransactionsByAccount(String accountNumber);
    List<TransactionDTO> getTransactionsByCustomer(UUID customerId);
    List<TransactionDTO> getAllTransactions();
    TransactionDTO reverseTransaction(UUID id, String reason);
}

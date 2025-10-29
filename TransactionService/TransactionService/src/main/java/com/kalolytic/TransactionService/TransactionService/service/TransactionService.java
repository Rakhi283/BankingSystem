package com.kalolytic.TransactionService.TransactionService.service;

import com.kalolytic.TransactionService.TransactionService.DTO.TransactionDTO;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TransactionService {

    TransactionDTO createTransaction(TransactionDTO dto);

    TransactionDTO getTransactionById(UUID id);

    List<TransactionDTO> getTransactionsByAccountNo(String accountNo);

    List<TransactionDTO> getAllTransactions();

    List<TransactionDTO> getTransactionsByCustomerAndAccount(UUID customerId, String accountNo);
}

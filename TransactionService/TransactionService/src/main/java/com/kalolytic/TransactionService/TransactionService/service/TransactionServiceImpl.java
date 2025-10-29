package com.kalolytic.TransactionService.TransactionService.service;

import com.kalolytic.TransactionService.TransactionService.DTO.TransactionDTO;
import com.kalolytic.TransactionService.TransactionService.Repo.TransactionRepo;
import com.kalolytic.TransactionService.TransactionService.mapper.TransactionMapper;
import com.kalolytic.TransactionService.TransactionService.model.ResourceNotFoundException;
import com.kalolytic.TransactionService.TransactionService.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private TransactionMapper transactionMapper;


    @Override
    @CacheEvict(value = {"transactions", "transactionsByAccount", "transactionsByCustomerAccount"}, allEntries = true)
    public TransactionDTO createTransaction(TransactionDTO dto) {
        Transaction entity = transactionMapper.toEntity(dto);
        entity.setTimestamp(Instant.now());
        Transaction saved = transactionRepo.save(entity);
        return transactionMapper.toDTO(saved);
    }


    @Override
    @Cacheable(value = "transaction", key = "#id")
    public TransactionDTO getTransactionById(UUID id) {
        Transaction transaction = transactionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found for ID: " + id));
        return transactionMapper.toDTO(transaction);
    }


    @Override
    @Cacheable(value = "transactionsByAccount", key = "#fromAccountNo")
    public List<TransactionDTO> getTransactionsByAccountNo(String fromAccountNo) {
        List<Transaction> transactions = transactionRepo.findByFromAccountNo(fromAccountNo);
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found for account: " + fromAccountNo);
        }
        return transactions.stream()
                .map(transactionMapper::toDTO)
                .toList();
    }


    @Override
    @Cacheable(value = "transactions")
    public List<TransactionDTO> getAllTransactions() {
        List<Transaction> allTransactions = transactionRepo.findAll();
        return allTransactions.stream()
                .map(transactionMapper::toDTO)
                .toList();
    }

    @Override
    @Cacheable(value = "transactionsByCustomerAccount", key = "#customerId.toString() + '_' + #fromAccountNo")
    public List<TransactionDTO> getTransactionsByCustomerAndAccount(UUID customerId, String fromAccountNo) {
        List<Transaction> transactions = transactionRepo.findByCustomerIdAndFromAccountNo(customerId, fromAccountNo);
        if (transactions.isEmpty()) {
            throw new ResourceNotFoundException("No transactions found for Customer ID: " + customerId + " and Account: " + fromAccountNo);
        }
        return transactions.stream()
                .map(transactionMapper::toDTO)
                .toList();
    }
}

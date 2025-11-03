package com.kalolytic.TransactionService.TransactionService.service;

import com.kalolytic.TransactionService.TransactionService.Repo.TransactionRepo;
import com.kalolytic.TransactionService.TransactionService.clients.AccountClient;
import com.kalolytic.TransactionService.TransactionService.clients.Customer;
import com.kalolytic.TransactionService.TransactionService.mapper.TransactionMapper;
import com.kalolytic.TransactionService.TransactionService.model.Transaction;
import com.kalolytic.commonModel.CommonModel.DTO.TransactionDTO;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountBalanceChangeRequest;
import com.kalolytic.commonModel.CommonModel.RequestDTO.TransactionRequestDTO;
import com.kalolytic.commonModel.CommonModel.enums.TransactionStatus;
import com.kalolytic.commonModel.CommonModel.enums.TransactionType;
import com.kalolytic.commonModel.CommonModel.exception.ResourceNotFoundException;
import com.kalolytic.commonModel.CommonModel.exception.TransactionException;
import com.kalolytic.commonModel.CommonModel.exception.TransactionNotFoundException;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger log = LoggerFactory.getLogger(TransactionServiceImpl.class);

    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private AccountClient accountclient;

    @Autowired
    private Customer customerClient;

    @Override
    @Transactional
    public TransactionDTO createTransaction(TransactionRequestDTO request) {
        log.info("Create transaction request received: {}", request);


// basic validation
        if (request == null || request.getFromAccountNo() == null || request.getToAccountNo() == null || request.getAmount() == null) {
            log.error("Invalid transaction request");
            throw new TransactionException("Invalid transaction request: missing fields");
        }


        if (request.getAmount() <= 0.0) {
            log.error("Amount must be greater than 0");
            throw new TransactionException("Amount must be positive");
        }


        Transaction transaction = Transaction.builder()
                .customerId(request.getCustomerId())
                .fromAccountNo(request.getFromAccountNo())
                .toAccountNo(request.getToAccountNo())
                .amount(request.getAmount())
                .remarks(request.getRemarks())
                .type(TransactionType.TRANSFER)
                .status(TransactionStatus.PENDING)
                .timestamp(LocalDateTime.now())
                .build();


        transaction = transactionRepo.save(transaction);


// perform debit then credit
        try {
            AccountBalanceChangeRequest debitReq = new AccountBalanceChangeRequest();
            debitReq.setAccountNumber(request.getFromAccountNo());
            debitReq.setAmount(request.getAmount());
            log.info("Calling account service to debit: {}", debitReq);
            accountclient.debitAmount(debitReq);


            AccountBalanceChangeRequest creditReq = new AccountBalanceChangeRequest();
            creditReq.setAccountNumber(request.getToAccountNo());
            creditReq.setAmount(request.getAmount());
            log.info("Calling account service to credit: {}", creditReq);
            accountclient.creditAmount(creditReq);


            transaction.setStatus(TransactionStatus.SUCCESS);
            transactionRepo.save(transaction);
            log.info("Transaction {} completed successfully", transaction.getId());


        } catch (Exception ex) {
            log.error("Transaction {} failed while contacting account service: {}", transaction.getId(), ex.getMessage());
            transaction.setStatus(TransactionStatus.FAILED);
            transaction.setFailureReason(ex.getMessage());
            transactionRepo.save(transaction);
// try to rollback if debit succeeded — complex; for now bubble up descriptive exception
            throw new TransactionException("Transaction failed: " + ex.getMessage());
        }


        return TransactionMapper.toDTO(transaction);
    }

    @Override
    public TransactionDTO getTransactionById(UUID id) {
        log.info("Fetching transaction by ID: {}", id);
        try {
            Transaction transaction = transactionRepo.findById(id)
                    .orElseThrow(() -> {
                        log.error("Transaction not found for ID: {}", id);
                        return new ResourceNotFoundException("Transaction not found for ID: " + id);
                    });

            log.info("Transaction found: {}", id);
            return transactionMapper.toDTO(transaction);

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error while fetching transaction by ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch transaction. Try again.");
        }
    }


    @Override
    public List<TransactionDTO> getTransactionsByAccount(String accountNumber) {
        log.info("Fetching transactions for account: {}", accountNumber);

        // Fetch both lists
        List<Transaction> from = transactionRepo.findAllByFromAccountNo(accountNumber);
        List<Transaction> to = transactionRepo.findAllByToAccountNo(accountNumber);

        // Combine both in one list
        List<Transaction> combined = new ArrayList<>();
        combined.addAll(from);
        combined.addAll(to);

        // Convert to DTO
        return combined.stream()
                .map(TransactionMapper::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public List<TransactionDTO> getTransactionsByCustomer(UUID customerId) {
        log.info("Fetching transactions for customer: {}", customerId);
        try{
            ResponseStructure<Boolean> exists = customerClient.existCustomer(customerId);
            if(exists.getData() != null || (boolean) exists.getData()) {
                List<Transaction> transactionByCustomer = transactionRepo.findAllByCustomerId(customerId);
                if (transactionByCustomer.isEmpty()) {
                    throw new TransactionNotFoundException("transactions are not found with id : " + customerId);
                }
                return transactionByCustomer.stream().map(TransactionMapper::toDTO).collect(Collectors.toList());
            }
            else{
                throw new ResourceNotFoundException("Customer does not exists ");
            }
        } catch (TransactionNotFoundException e) {
            throw new TransactionNotFoundException("transactions are not found with id : "+ customerId);
        }catch(Exception e){
            log.error("Unexpected error occur during fetching the transaction with this customer");
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        log.info("Fetching all transactions");
        return transactionRepo.findAll().stream().map(TransactionMapper::toDTO).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public TransactionDTO reverseTransaction(UUID id, String reason) {
        log.info("Request to reverse transaction: {} reason: {}", id, reason);
        Transaction t = transactionRepo.findById(id).orElseThrow(() -> new TransactionNotFoundException("Transaction not found: " + id));


        if (t.getStatus() != TransactionStatus.SUCCESS) {
            log.error("Only successful transactions can be reversed. Current status: {}", t.getStatus());
            throw new TransactionException("Only successful transactions can be reversed");
        }


        try {
// debit the receiver, credit the sender to rollback
            AccountBalanceChangeRequest debitReq = new AccountBalanceChangeRequest();
            debitReq.setAccountNumber(t.getToAccountNo());
            debitReq.setAmount(t.getAmount());
            accountclient.debitAmount(debitReq);


            AccountBalanceChangeRequest creditReq = new AccountBalanceChangeRequest();
            creditReq.setAccountNumber(t.getFromAccountNo());
            creditReq.setAmount(t.getAmount());
            accountclient.creditAmount(creditReq);


            t.setStatus(TransactionStatus.REVERSED);
            t.setFailureReason(reason);
            transactionRepo.save(t);


        } catch (Exception ex) {
            log.error("Failed to reverse transaction {}: {}", id, ex.getMessage());
            throw new TransactionException("Reverse failed: " + ex.getMessage());
        }


        return TransactionMapper.toDTO(t);
    }
}

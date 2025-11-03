package com.kalolytic.TransactionService.TransactionService.Repo;


import com.kalolytic.TransactionService.TransactionService.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,UUID> {

    List<Transaction> findAllByFromAccountNo(String fromAccountNo);
    List<Transaction> findAllByToAccountNo(String toAccountNo);
    List<Transaction> findAllByCustomerId(UUID customerId);

}

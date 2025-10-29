package com.kalolytic.TransactionService.TransactionService.Repo;


import com.kalolytic.TransactionService.TransactionService.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction,UUID> {
    List<Transaction> findByFromAccountNo(String fromAccountNo);

    List<Transaction> findByCustomerIdAndFromAccountNo(UUID customerId, String fromAccountNo);
}

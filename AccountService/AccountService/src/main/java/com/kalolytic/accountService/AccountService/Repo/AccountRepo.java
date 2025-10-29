package com.kalolytic.accountService.AccountService.Repo;

import com.kalolytic.accountService.AccountService.model.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccountRepo extends JpaRepository<AccountEntity, UUID> {
    List<AccountEntity> findAllByCustomerId(UUID customerId);
}

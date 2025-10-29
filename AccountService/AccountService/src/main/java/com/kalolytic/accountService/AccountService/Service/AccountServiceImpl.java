package com.kalolytic.accountService.AccountService.Service;

import com.kalolytic.accountService.AccountService.DTO.AccountDTO;
import com.kalolytic.accountService.AccountService.Repo.AccountRepo;
import com.kalolytic.accountService.AccountService.mapper.AccMapper;
import com.kalolytic.accountService.AccountService.model.AccountEntity;
import com.kalolytic.accountService.AccountService.model.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccMapper accMapper;

    @Override
    @CacheEvict(value = {"account", "accountByCustomer"}, allEntries = true)
    public AccountDTO create(AccountDTO dto) {
        AccountEntity entity = accMapper.toEntity(dto);
        AccountEntity saved = accountRepo.save(entity);
        return accMapper.toDto(saved);
    }


    @Override
    @Cacheable(value = "account", key = "#id")
    public AccountDTO getById(UUID id) {
        AccountEntity entity = accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for ID: " + id));
        return accMapper.toDto(entity);
    }


    @Override
    @Cacheable(value = "accountByCustomer", key = "#customerId")
    public List<AccountDTO> getByCustomer(UUID customerId) {
        List<AccountEntity> accounts = accountRepo.findAllByCustomerId(customerId);

        if (accounts.isEmpty()) {
            throw new ResourceNotFoundException("No accounts found for Customer ID: " + customerId);
        }

        return accounts.stream()
                .map(AccMapper::toDto)
                .toList();
    }


    @Override
    @CacheEvict(value = {"account", "accountByCustomer"}, allEntries = true)
    public AccountDTO updateAccount(UUID id, AccountDTO dto) {
        AccountEntity existing = accountRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found for ID: " + id));

        existing.setAccountNo(dto.getAccountNo());
        existing.setBalance(dto.getBalance());
        existing.setCustId(dto.getCustId());

        AccountEntity updated = accountRepo.save(existing);
        return accMapper.toDto(updated);
    }

    @Override
    @CacheEvict(value = {"account", "accountByCustomer"}, allEntries = true)
    public String deleteAccount(UUID id) {
        if (!accountRepo.existsById(id)) {
            throw new ResourceNotFoundException("Account not found for ID: " + id);
        }
        accountRepo.deleteById(id);
        return "Account deleted successfully (ID: " + id + ")";
    }

    @Override
    @Cacheable(
            value = "accountBelongsToCustomerCache",
            key = "#accountId.toString() + '_' + #customerId.toString()"
    )
    public boolean accountBelongsToCustomer(UUID accountId, UUID customerId) {
        Optional<AccountEntity> optional = accountRepo.findById(accountId);
        return optional.map(account -> account.getCustId().equals(customerId)).orElse(false);
    }
}

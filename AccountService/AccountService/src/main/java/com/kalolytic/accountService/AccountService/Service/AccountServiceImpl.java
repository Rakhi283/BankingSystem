package com.kalolytic.accountService.AccountService.Service;

import com.kalolytic.accountService.AccountService.Repo.AccountRepo;
import com.kalolytic.accountService.AccountService.client.CustomerClients;
import com.kalolytic.accountService.AccountService.mapper.AccMapper;
import com.kalolytic.accountService.AccountService.model.AccountEntity;
import com.kalolytic.accountService.AccountService.utility.AccountNumberGenerator;
import com.kalolytic.commonModel.CommonModel.DTO.AccountDTO;
import com.kalolytic.commonModel.CommonModel.DTO.AccountUpdateDTO;
import com.kalolytic.commonModel.CommonModel.DTO.AccountWithoutCustomerDTO;
import com.kalolytic.commonModel.CommonModel.DTO.CustomerDTO;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountVerificationRequest;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountBalanceChangeRequest;
import com.kalolytic.commonModel.CommonModel.exception.AccountNotFoundException;
import com.kalolytic.commonModel.CommonModel.exception.InsufficientBalanceException;
import com.kalolytic.commonModel.CommonModel.exception.InvalidInputException;

import com.kalolytic.commonModel.CommonModel.exception.ResourceNotFoundException;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private AccMapper accMapper;

    @Autowired
    private CustomerClients customerClient;

    @Autowired
    private AccountNumberGenerator accountNumberGenerator;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;



    @Override
    @CacheEvict(value = {"account", "accountByCustomer"}, allEntries = true)
    public AccountDTO create(AccountDTO dto) {

        logger.info("Creating account for customerId: {}", dto.getCustomerId());

        if (dto.getBalance() != null && dto.getBalance() < 0.0) {
            logger.warn("Invalid balance amount provided: {}", dto.getBalance());
            throw new InvalidInputException("Please Enter a valid balance amount!");
        }

        AccountEntity entity = accMapper.toEntity(dto);

        // Generate unique external accountId
        entity.setAccountId(UUID.randomUUID());

        logger.debug("Saving AccountEntity to generate ID...");
        AccountEntity saved = accountRepo.save(entity);

        // Generate account number
        String accountNumber = accountNumberGenerator.generate(saved.getBranchCode(), saved.getId());
        saved.setAccountNumber(accountNumber);

        ResponseStructure<?> response = customerClient.getCustomer(dto.getCustomerId());

        CustomerDTO customer = objectMapper.convertValue(response.getData(), CustomerDTO.class);
        // ✅ Set name & email before saving
        entity.setCustomerName(
                customer.getFirstName() +
                        (customer.getLastName() != null ? " " + customer.getLastName() : "")
        );
        entity.setCustomerEmail(customer.getEmail());


        if(customer == null) {
            logger.error("Customer details missing for customerId: {}", dto.getCustomerId());
            throw new ResourceNotFoundException("Unable to fetch customer details");
        }

        logger.info("Customer details fetched: {} {}", customer.getFirstName(), customer.getLastName());


        logger.debug("Account Number generated: {}", accountNumber);

        // Save again with updated account number
        saved = accountRepo.save(saved);

        logger.info("Account created successfully for customerId: {}", dto.getCustomerId());
        return accMapper.toDto(saved,customer);
    }


    @Override
//    @Cacheable(value = "account", key = "#id")
    public AccountWithoutCustomerDTO getById(UUID id) {
        logger.info("Fetching account by ID: {}", id);

        AccountEntity entity = accountRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Account not found for ID: {}", id);
                    return new ResourceNotFoundException("Account not found for ID: " + id);
                });

        logger.info("Account fetched successfully for ID: {}", id);
        return accMapper.toWithoutCustomerDTO(entity);
    }


    @Override
    @Cacheable(value = "accountByCustomer", key = "#customerId")
    public List<AccountDTO> getByCustomer(UUID customerId) {

        logger.info("Fetching accounts for customerId: {}", customerId);

        ResponseStructure<?> response = customerClient.getCustomer(customerId);

        CustomerDTO customer = objectMapper.convertValue(response.getData(), CustomerDTO.class);

        if(customer == null) {
            logger.error("Customer details missing for customerId: {}", customerId);
            throw new ResourceNotFoundException("Unable to fetch customer details");
        }

        logger.info("Customer details fetched: {} {}", customer.getFirstName(), customer.getLastName());


        List<AccountEntity> accounts = accountRepo.findAllByCustomerId(customerId);

        if (accounts.isEmpty()) {
            logger.warn("No accounts found for customerId: {}", customerId);
            throw new ResourceNotFoundException("No accounts found for Customer ID: " + customerId);
        }

        logger.info("Accounts fetched successfully for customerId: {}", customerId);
        return accounts.stream().map(account -> AccMapper.toDto(account, customer)).toList();
    }


    @Override
    @CacheEvict(value = {"account", "accountByCustomer"}, allEntries = true)
    public AccountUpdateDTO updateAccount(UUID id, AccountUpdateDTO dto) {

        logger.info("Updating account for ID: {}", id);

        AccountEntity existing = accountRepo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Account not found for ID: {}", id);
                    return new ResourceNotFoundException("Account not found for ID: " + id);
                });

        if (dto.getBalance() != null) {
            existing.setBalance(dto.getBalance());
        }
        if (dto.getAccountType() != null) {
            existing.setAccountType(dto.getAccountType());
        }
        if(dto.getCustomerEmail()!=null){
            existing.setCustomerName(dto.getCustomerEmail());
        }
        if(dto.getCustomerName() != null){
            existing.setCustomerName(dto.getCustomerName());
        }


        AccountEntity updated = accountRepo.save(existing);
        logger.info("Account updated successfully for ID: {}", id);

        return accMapper.toUpdateDTO(updated);
    }


    @Override
    @CacheEvict(value = {"account", "accountByCustomer"}, allEntries = true)
    public String deleteAccount(UUID id) {

        logger.info("Deleting account for ID: {}", id);

        if (!accountRepo.existsById(id)) {
            logger.error("Account not found for ID: {}", id);
            throw new ResourceNotFoundException("Account not found for ID: " + id);
        }

        accountRepo.deleteById(id);
        logger.info("Account deleted successfully (ID: {})", id);

        return "Account deleted successfully (ID: " + id + ")";
    }


    @Override
//    @Cacheable(value = "accountBelongsToCustomerCache", key = "#request.accountId.toString() + '_' + #request.customerId.toString()")
    public boolean accountBelongsToCustomer(AccountVerificationRequest request) {

        logger.info("Checking account ownership -> accountId: {}, customerId: {}", request.getAccountId(), request.getCustomerId());

        Optional<AccountEntity> optional = accountRepo.findByAccountId(request.getAccountId());
        if(optional.isEmpty()){
            throw new AccountNotFoundException("Account not found with id :"+request.getAccountId());
        }
        boolean result = optional.map(account -> account.getAccountId().equals(request.getAccountId())).orElse(false);

        logger.info("Ownership check result: {}", result);
        return result;
    }

    @Override
    @Transactional
    public AccountBalanceChangeRequest creditAmount(AccountBalanceChangeRequest transaction) {

        logger.info("Credit Request Received: {}", transaction);

        if (transaction.getAccountNumber() == null || transaction.getAmount() == null || transaction.getAmount() <= 0) {
            logger.error("Invalid credit request data");
            throw new IllegalArgumentException("Account number and positive amount are required.");
        }

        AccountEntity account = accountRepo.findByAccountNumber(transaction.getAccountNumber())
                .orElseThrow(() -> {
                    logger.error("Account not found for accountNumber: {}", transaction.getAccountNumber());
                    return new AccountNotFoundException("Account not found");
                });

        account.setBalance(account.getBalance() + transaction.getAmount());
        accountRepo.save(account);

        logger.info("Amount {} credited to account {}", transaction.getAmount(), transaction.getAccountNumber());

        return transaction;
    }

    @Override
    @Transactional
    public AccountBalanceChangeRequest DebitAmount(AccountBalanceChangeRequest transaction) {

        logger.info("Debit Request Received: {}", transaction);

        if (transaction.getAccountNumber() == null || transaction.getAmount() == null || transaction.getAmount() <= 0) {
            logger.error("Invalid debit request data");
            throw new IllegalArgumentException("Account number and positive amount are required.");
        }

        AccountEntity account = accountRepo.findByAccountNumber(transaction.getAccountNumber())
                .orElseThrow(() -> {
                    logger.error("Account not found for accountNumber: {}", transaction.getAccountNumber());
                    return new AccountNotFoundException("Account not found");
                });

        if (account.getBalance() < transaction.getAmount()) {
            logger.error("Insufficient balance for debit request. Current: {}, Requested: {}",
                    account.getBalance(), transaction.getAmount());
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - transaction.getAmount());
        accountRepo.save(account);

        logger.info("Amount {} debited from account {}", transaction.getAmount(), transaction.getAccountNumber());

        return transaction;
    }

    @Override
    public List<AccountWithoutCustomerDTO> getAllAccounts() {
        logger.info("Request received to fetch all accounts");

        try {
            List<AccountEntity> accounts = accountRepo.findAll();

            if (accounts.isEmpty()) {
                logger.warn("No accounts found in the database");
                return Collections.emptyList();
            }

            logger.info("Total accounts fetched: {}", accounts.size());

            return accounts.stream()
                    .map(AccMapper::toWithoutCustomerDTO)
                    .collect(Collectors.toList());

        } catch (Exception ex) {
            logger.error("Error occurred while fetching accounts: {}", ex.getMessage(), ex);
            throw new ResourceNotFoundException("Failed to fetch accounts, please try again later");
        }
    }
}

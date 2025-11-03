package com.kalolytic.accountService.AccountService.mapper;


import com.kalolytic.accountService.AccountService.model.AccountEntity;

import com.kalolytic.commonModel.CommonModel.DTO.AccountDTO;
import com.kalolytic.commonModel.CommonModel.DTO.AccountUpdateDTO;
import com.kalolytic.commonModel.CommonModel.DTO.AccountWithoutCustomerDTO;
import com.kalolytic.commonModel.CommonModel.DTO.CustomerDTO;
import com.kalolytic.customerService.CustomerService.model.Customer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccMapper {

    public static AccountEntity toWithoutCustomerEntity(AccountWithoutCustomerDTO dto) {
        if (dto == null) return null;
       AccountEntity entity = new AccountEntity();
       entity.setAccountNumber(dto.getAccountNumber());
       entity.setCustomerId(dto.getCustomerId());
       entity.setAccountId(dto.getAccountId());
       entity.setBalance(dto.getBalance());
       entity.setBranchCode(dto.getBranchCode());
       entity.setAccountType(dto.getAccountType());
       entity.setCreatedAt(LocalDateTime.now());
       return entity;
    }

    public static AccountEntity toEntity(AccountDTO dto) {
        if (dto == null) return null;
        AccountEntity entity = new AccountEntity();
        entity.setAccountNumber(dto.getAccountNumber());
        entity.setCustomerId(dto.getCustomerId());
        entity.setAccountId(dto.getAccountId());
        entity.setBalance(dto.getBalance());
        entity.setBranchCode(dto.getBranchCode());
        entity.setAccountType(dto.getAccountType());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCustomerName(dto.getCustomerName());
        entity.setCustomerEmail(dto.getCustomerEmail());
        return entity;
    }

    // Convert Entity to DTO
    public static AccountDTO toDto(AccountEntity entity, CustomerDTO customerDTO) {
        if (entity == null) return null;

        AccountDTO dto = new AccountDTO();
        dto.setId(entity.getId());
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setAccountId(entity.getAccountId());
        dto.setCustomerId(entity.getCustomerId());
        dto.setAccountType(entity.getAccountType());
        dto.setBranchCode(entity.getBranchCode());
        dto.setBalance(entity.getBalance());
        dto.setCreatedAt(entity.getCreatedAt());
        // Now set customer details also
        if (customerDTO != null) {
            dto.setCustomerName(customerDTO.getFirstName() +
                    (customerDTO.getLastName() != null ? " " + customerDTO.getLastName() : ""));
            dto.setCustomerEmail(customerDTO.getEmail());
        }
        return dto;
    }

    // Convert Entity to DTO
    public static AccountWithoutCustomerDTO toWithoutCustomerDTO(AccountEntity entity) {
        if (entity == null) return null;

        AccountWithoutCustomerDTO dto = new AccountWithoutCustomerDTO();
        dto.setId(entity.getId());
        dto.setAccountId(entity.getAccountId());
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setCustomerId(entity.getCustomerId());
        dto.setAccountType(entity.getAccountType());
        dto.setBalance(entity.getBalance());
        dto.setBranchCode(entity.getBranchCode());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }


public static AccountUpdateDTO toUpdateDTO(AccountEntity entity){
    if (entity == null) return null;

    AccountUpdateDTO dto = new AccountUpdateDTO();
    dto.setAccountType(entity.getAccountType());
    dto.setBalance(entity.getBalance());
    dto.setCustomerName(entity.getCustomerName());
    dto.setCustomerEmail(entity.getCustomerEmail());
    return dto;
}



}

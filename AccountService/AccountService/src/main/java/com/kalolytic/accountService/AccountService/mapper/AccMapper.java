package com.kalolytic.accountService.AccountService.mapper;

import com.kalolytic.accountService.AccountService.DTO.AccountDTO;
import com.kalolytic.accountService.AccountService.model.AccountEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccMapper {

    public static AccountEntity toEntity(AccountDTO dto) {
        if (dto == null) return null;
       AccountEntity entity = new AccountEntity();
       entity.setId(dto.getId());
       entity.setAccountNo(dto.getAccountNo());
       entity.setCustId(dto.getCustId());
       entity.setBalance(dto.getBalance());
       entity.setCreatedAt(LocalDateTime.now());
       entity.setUpdatedAt(LocalDateTime.now());
       return entity;
    }

    // Convert Entity to DTO
    public static AccountDTO toDto(AccountEntity entity) {
        if (entity == null) return null;

        AccountDTO dto = new AccountDTO();
        dto.setId(entity.getId());
        dto.setAccountNo(entity.getAccountNo());
        dto.setCustId(entity.getCustId());
//        dto.setAccountType(entity.getAccountType());
        dto.setBalance(entity.getBalance());
//        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}

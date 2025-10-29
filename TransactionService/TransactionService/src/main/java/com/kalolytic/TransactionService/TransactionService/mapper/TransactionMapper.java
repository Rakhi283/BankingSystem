package com.kalolytic.TransactionService.TransactionService.mapper;

import com.kalolytic.TransactionService.TransactionService.DTO.TransactionDTO;
import com.kalolytic.TransactionService.TransactionService.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionDTO toDTO(Transaction entity) {
        TransactionDTO dto = new TransactionDTO();
        dto.setAmount(entity.getAmount());
        dto.setTimestamp(entity.getTimestamp());
        dto.setTransId(entity.getTransId());
        dto.setFromAccountNo(entity.getFromAccountNo());
        dto.setToAccountNo(entity.getToAccountNo());
        return dto;
    }

    public Transaction toEntity(TransactionDTO dto) {
        Transaction entity = new Transaction();
       entity.setAmount(dto.getAmount());
       entity.setTimestamp(dto.getTimestamp());
       entity.setTransId(dto.getTransId());
       entity.setFromAccountNo(dto.getFromAccountNo());
       entity.setToAccountNo(dto.getToAccountNo());
        return entity;
    }
}

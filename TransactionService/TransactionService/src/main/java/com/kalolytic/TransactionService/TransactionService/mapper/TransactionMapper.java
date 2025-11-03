package com.kalolytic.TransactionService.TransactionService.mapper;

import com.kalolytic.TransactionService.TransactionService.model.Transaction;
import com.kalolytic.commonModel.CommonModel.DTO.TransactionDTO;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public static TransactionDTO toDTO(Transaction t) {
        if (t == null) return null;
        TransactionDTO dto = new TransactionDTO();
        dto.setId(t.getId());
        dto.setCustomerId(t.getCustomerId());
        dto.setFromAccountNo(t.getFromAccountNo());
        dto.setToAccountNo(t.getToAccountNo());
        dto.setAmount(t.getAmount());
        dto.setRemarks(t.getRemarks());
        dto.setType(t.getType() == null ? null : t.getType().name());
        dto.setStatus(t.getStatus() == null ? null : t.getStatus().name());
        dto.setTimestamp(t.getTimestamp());
        dto.setFailureReason(t.getFailureReason());
        return dto;
    }

}

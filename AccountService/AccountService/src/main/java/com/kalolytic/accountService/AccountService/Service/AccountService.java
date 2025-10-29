package com.kalolytic.accountService.AccountService.Service;

import com.kalolytic.accountService.AccountService.DTO.AccountDTO;
import com.kalolytic.commonModel.CommonModel.response.ResponseStructure;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO create(AccountDTO dto);

    AccountDTO getById(UUID id);

    List<AccountDTO> getByCustomer(UUID customerId);

    AccountDTO updateAccount(UUID id, AccountDTO dto);

    String deleteAccount(UUID id);

    boolean accountBelongsToCustomer(UUID accountId, UUID customerId);
}

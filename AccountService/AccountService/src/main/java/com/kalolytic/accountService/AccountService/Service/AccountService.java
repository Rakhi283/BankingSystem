package com.kalolytic.accountService.AccountService.Service;



import com.kalolytic.commonModel.CommonModel.DTO.AccountDTO;
import com.kalolytic.commonModel.CommonModel.DTO.AccountUpdateDTO;
import com.kalolytic.commonModel.CommonModel.DTO.AccountWithoutCustomerDTO;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountVerificationRequest;
import com.kalolytic.commonModel.CommonModel.RequestDTO.AccountBalanceChangeRequest;

import java.util.List;
import java.util.UUID;

public interface AccountService {

    AccountDTO create(AccountDTO dto);

    AccountWithoutCustomerDTO getById(UUID id);

    List<AccountDTO> getByCustomer(UUID customerId);

    AccountUpdateDTO updateAccount(UUID id, AccountUpdateDTO dto);

    String deleteAccount(UUID id);

    boolean accountBelongsToCustomer(AccountVerificationRequest request);

    AccountBalanceChangeRequest creditAmount(AccountBalanceChangeRequest transaction);

    AccountBalanceChangeRequest DebitAmount(AccountBalanceChangeRequest transaction);

    List<AccountWithoutCustomerDTO> getAllAccounts();
}

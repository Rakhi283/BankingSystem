package com.kalolytic.commonModel.CommonModel.RequestDTO;

import java.util.UUID;
import lombok.Data;

@Data
public class AccountVerificationRequest {
    private UUID accountId;
    private UUID customerId;
}


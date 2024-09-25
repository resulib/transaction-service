package com.resul.transactionservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateWithdrawRequest {
    private Long accountId;
    private String amount;
    private String description;
}

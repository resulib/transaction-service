package com.resul.transactionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateDepositRequest {
    private Long accountId;
    private BigDecimal amount;
    private String description;
}

package com.resul.transactionservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class AccountDto {
    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private String currency;
    private String accountStatus;
    private Long userId;
}

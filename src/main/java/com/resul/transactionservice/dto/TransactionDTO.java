package com.resul.transactionservice.dto;

import com.resul.transactionservice.entity.TransactionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Getter
@Setter
public class TransactionDTO {
    private Long id;
    private Long accountId;
    private BigDecimal amount;
    private String description;
    private TransactionType transactionType;
    private TransactionStatus status;
    private LocalDateTime transactionTime;

}
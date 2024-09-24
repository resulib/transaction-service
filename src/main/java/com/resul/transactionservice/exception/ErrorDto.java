package com.resul.transactionservice.exception;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ErrorDto {
    private String message;
    private LocalDateTime timestamp;
}

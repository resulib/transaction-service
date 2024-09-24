package com.resul.transactionservice.controller;

import com.resul.transactionservice.dto.CreateDepositRequest;
import com.resul.transactionservice.dto.TransactionResponse;
import com.resul.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;



    @PostMapping
    public ResponseEntity<TransactionResponse> createDepositRequest(@RequestBody CreateDepositRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createDepositTransaction(request));
    }

}

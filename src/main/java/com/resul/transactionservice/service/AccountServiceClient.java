package com.resul.transactionservice.service;

import com.resul.transactionservice.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "account-service", url = "http://localhost:8090/api/v1/accounts")
public interface AccountServiceClient {

    @PostMapping()
    AccountDto getAccountById(Long id);

    @PatchMapping("/api/v1/accounts/{accountId}/balance")
    void updateBalance(@PathVariable("accountId") Long accountId, @RequestBody BigDecimal amount);
}

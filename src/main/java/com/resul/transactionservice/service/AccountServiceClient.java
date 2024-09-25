package com.resul.transactionservice.service;

import com.resul.transactionservice.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "account-service", url = "http://localhost:8093/api/v1/accounts")
public interface AccountServiceClient {

    @GetMapping("/{id}")
    AccountDto getAccountById(@PathVariable Long id);

    @PutMapping("/{accountId}/balance")
    void updateBalance(@PathVariable("accountId") Long accountId, @RequestBody String amount);
}

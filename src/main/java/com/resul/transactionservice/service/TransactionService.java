package com.resul.transactionservice.service;

import com.resul.transactionservice.dto.AccountDto;
import com.resul.transactionservice.dto.CreateDepositRequest;
import com.resul.transactionservice.dto.CreateWithdrawRequest;
import com.resul.transactionservice.dto.TransactionResponse;
import com.resul.transactionservice.entity.TransactionEntity;
import com.resul.transactionservice.entity.TransactionStatus;
import com.resul.transactionservice.entity.TransactionType;
import com.resul.transactionservice.exception.InsufficientFundsException;
import com.resul.transactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountServiceClient accountServiceClient;

    @Transactional
    public TransactionResponse createDepositTransaction(CreateDepositRequest request) {
        BigDecimal bigDecimalAmount = new BigDecimal(request.getAmount());
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountId(request.getAccountId());
        transaction.setAmount(bigDecimalAmount);
        transaction.setTransactionType(TransactionType.DEPOSIT);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);
        try {
            accountServiceClient.updateBalance(request.getAccountId(), request.getAmount());
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
        }
        return new TransactionResponse(transaction.getStatus().name());
    }

    @Transactional
    public TransactionResponse createWithdrawTransaction(CreateWithdrawRequest request) {
        BigDecimal bigDecimalAmount = new BigDecimal(request.getAmount());

        AccountDto account = accountServiceClient.getAccountById(request.getAccountId());
        if (account.getBalance().compareTo(bigDecimalAmount) < 0) {
            throw new InsufficientFundsException("Not enough balance. Your balance: " + account.getBalance());
        }

        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountId(request.getAccountId());
        transaction.setAmount(bigDecimalAmount);
        transaction.setTransactionType(TransactionType.WITHDRAW);
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setTransactionTime(LocalDateTime.now());
        transaction = transactionRepository.save(transaction);
        try {
            String amount = String.valueOf(bigDecimalAmount.negate());
            accountServiceClient.updateBalance(request.getAccountId(), amount);
            transaction.setStatus(TransactionStatus.SUCCESS);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatus.FAILED);
        }
        return new TransactionResponse(transaction.getStatus().name());
    }
}

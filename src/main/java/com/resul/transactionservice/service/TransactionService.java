package com.resul.transactionservice.service;

import com.resul.transactionservice.dto.CreateDepositRequest;
import com.resul.transactionservice.dto.TransactionResponse;
import com.resul.transactionservice.entity.TransactionEntity;
import com.resul.transactionservice.entity.TransactionStatus;
import com.resul.transactionservice.entity.TransactionType;
import com.resul.transactionservice.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountServiceClient accountServiceClient;

    public TransactionResponse createDepositTransaction(CreateDepositRequest request){
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAccountId(request.getAccountId());
        transaction.setAmount(request.getAmount());
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
}

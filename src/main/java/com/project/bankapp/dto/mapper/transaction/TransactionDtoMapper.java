package com.project.bankapp.dto.mapper.transaction;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.entity.Transaction;
import com.project.bankapp.entity.enums.CurrencyCode;
import com.project.bankapp.entity.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TransactionDtoMapper {
    public TransactionDto mapEntityToDto(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("transaction cannot be null");
        }
        return TransactionDto.builder()
                .debitAccountUuid(transaction.getDebitAccountUuid() != null ? transaction.getDebitAccountUuid().toString() : null)
                .creditAccountUuid(transaction.getCreditAccountUuid() != null ? transaction.getCreditAccountUuid().toString() : null)
                .type(transaction.getType() != null ? transaction.getType().name() : null)
                .currencyCode(transaction.getCurrencyCode() != null ? transaction.getCurrencyCode().name() : null)
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .build();
    }

    public Transaction mapDtoToEntity(TransactionDto transactionDto) {
        if (transactionDto == null) {
            throw new IllegalArgumentException("transactionDto cannot be null");
        }
        return Transaction.builder()
                .debitAccountUuid(transactionDto.getDebitAccountUuid() != null ? UUID.fromString(transactionDto.getDebitAccountUuid()) : null)
                .creditAccountUuid(transactionDto.getCreditAccountUuid() != null ? UUID.fromString(transactionDto.getCreditAccountUuid()) : null)
                .type(transactionDto.getType() != null ? TransactionType.valueOf(transactionDto.getType()) : null)
                .currencyCode(transactionDto.getCurrencyCode() != null ? CurrencyCode.valueOf(transactionDto.getCurrencyCode()) : null)
                .amount(transactionDto.getAmount())
                .description(transactionDto.getDescription())
                .build();
    }
}

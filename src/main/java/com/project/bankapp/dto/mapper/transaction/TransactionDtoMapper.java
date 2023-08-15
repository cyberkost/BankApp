package com.project.bankapp.dto.mapper.transaction;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.entity.Transaction;
import com.project.bankapp.entity.enums.CurrencyCode;
import com.project.bankapp.entity.enums.TransactionType;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Component class responsible for mapping between Transaction entities and TransactionDto objects.
 */
@Component
public class TransactionDtoMapper {
    /**
     * Maps a Transaction entity to a TransactionDto.
     *
     * @param transaction The Transaction entity to be mapped.
     * @return A TransactionDto containing the mapped information.
     * @throws IllegalArgumentException if the provided transaction is null.
     */
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

    /**
     * Maps a TransactionDto to a Transaction entity.
     *
     * @param transactionDto The TransactionDto to be mapped.
     * @return A Transaction entity containing the mapped information.
     * @throws IllegalArgumentException if the provided transactionDto is null.
     */
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

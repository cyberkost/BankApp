package com.project.bankapp.dto.mapper.transaction;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Component class responsible for mapping between Transaction and TransactionDto objects.
 */
@Component
public class TransactionCreationMapper {
    /**
     * Maps a Transaction entity to a TransactionDto.
     *
     * @param entity The Transaction entity to be mapped.
     * @return The corresponding TransactionDto.
     * @throws IllegalArgumentException If the provided entity is null.
     */
    public TransactionDto mapEntityToDto(Transaction entity) {
        if (entity == null) {
            throw new IllegalArgumentException("transaction cannot be null");
        }
        return TransactionDto.builder()
                .creditAccountUuid(entity.getCreditAccountUuid() != null ? String.valueOf(entity.getCreditAccountUuid()) : null)
                .amount(entity.getAmount())
                .description(entity.getDescription())
                .build();
    }

    /**
     * Maps a TransactionDto to a Transaction entity.
     *
     * @param entityDto The TransactionDto to be mapped.
     * @return The corresponding Transaction entity.
     * @throws IllegalArgumentException If the provided entityDto is null.
     */
    public Transaction mapDtoToEntity(TransactionDto entityDto) {
        if (entityDto == null) {
            throw new IllegalArgumentException("transaction cannot be null");
        }
        return Transaction.builder()
                .creditAccountUuid(entityDto.getCreditAccountUuid() != null ? UUID.fromString(entityDto.getCreditAccountUuid()) : null)
                .amount(entityDto.getAmount())
                .description(entityDto.getDescription())
                .build();
    }
}

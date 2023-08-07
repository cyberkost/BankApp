package com.project.bankapp.dto.mapper.transaction;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.entity.Transaction;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class TransactionCreationMapper {
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

package com.project.bankapp.dto.mapper.account;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountType;
import com.project.bankapp.entity.enums.CurrencyCode;
import org.springframework.stereotype.Component;

@Component
public class AccountCreationMapper {
    public AccountDto mapEntityToDto(Account entity) {
        if (entity == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return AccountDto.builder()
                .name(entity.getName())
                .type(entity.getType() != null ? entity.getType().name() : null)
                .currencyCode(entity.getCurrencyCode() != null ? entity.getCurrencyCode().name() : null)
                .build();
    }

    public Account mapDtoToEntity(AccountDto entityDto) {
        if (entityDto == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return Account.builder()
                .name(entityDto.getName())
                .type(entityDto.getType() != null ? AccountType.valueOf(entityDto.getType()) : null)
                .currencyCode(entityDto.getCurrencyCode() != null ? CurrencyCode.valueOf(entityDto.getCurrencyCode()) : null)
                .build();
    }
}

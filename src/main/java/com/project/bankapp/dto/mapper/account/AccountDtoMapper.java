package com.project.bankapp.dto.mapper.account;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountStatus;
import com.project.bankapp.entity.enums.AccountType;
import com.project.bankapp.entity.enums.CurrencyCode;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Component class responsible for mapping between Account and AccountDto objects.
 */
@Component
public class AccountDtoMapper {
    /**
     * Maps an Account entity to an AccountDto.
     *
     * @param account The Account entity to be mapped.
     * @return The corresponding AccountDto.
     * @throws IllegalArgumentException if the provided account is null.
     */
    public AccountDto mapEntityToDto(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return AccountDto.builder()
                .clientUuid(account.getClientUuid() != null ? account.getClientUuid().toString() : null)
                .name(account.getName())
                .type(account.getName() != null ? account.getType().name() : null)
                .status(account.getStatus() != null ? account.getStatus().name() : null)
                .balance(account.getBalance())
                .currencyCode(account.getCurrencyCode() != null ? account.getCurrencyCode().name() : null)
                .build();
    }

    /**
     * Maps an AccountDto to an Account entity.
     *
     * @param accountDto The AccountDto to be mapped.
     * @return The corresponding Account entity.
     * @throws IllegalArgumentException if the provided accountDto is null.
     */
    public Account mapDtoToEntity(AccountDto accountDto) {
        if (accountDto == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return Account.builder()
                .clientUuid(accountDto.getClientUuid() != null ? UUID.fromString(accountDto.getClientUuid()) : null)
                .name(accountDto.getName())
                .type(accountDto.getType() != null ? AccountType.valueOf(accountDto.getType()) : null)
                .status(accountDto.getStatus() != null ? AccountStatus.valueOf(accountDto.getStatus()) : null)
                .balance(accountDto.getBalance())
                .currencyCode(accountDto.getCurrencyCode() != null ? CurrencyCode.valueOf(accountDto.getCurrencyCode()) : null)
                .build();
    }
}

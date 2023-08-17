package com.project.bankapp.dto.mapper.account;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountType;
import com.project.bankapp.entity.enums.CurrencyCode;
import org.springframework.stereotype.Component;

/**
 * Component class responsible for mapping between Account entities and AccountDto data transfer objects.
 */
@Component
public class AccountCreationMapper {
    /**
     * Maps an Account entity to an AccountDto.
     *
     * @param entity The Account entity to be mapped.
     * @return An AccountDto containing the mapped information.
     * @throws IllegalArgumentException If the input entity is null.
     */
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

    /**
     * Maps an AccountDto to an Account entity.
     *
     * @param entityDto The AccountDto to be mapped.
     * @return An Account entity containing the mapped information.
     * @throws IllegalArgumentException If the input entityDto is null.
     */
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

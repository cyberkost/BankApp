package com.project.bankapp.dto.mapper.account;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountStatus;
import org.springframework.stereotype.Component;

/**
 * Component class responsible for mapping between Account entities and AccountDto data transfer objects.
 */
@Component
public class AccountUpdateMapper {
    /**
     * Maps an Account entity to an AccountDto.
     *
     * @param entity The Account entity to be mapped.
     * @return The corresponding AccountDto.
     * @throws IllegalArgumentException if the provided entity is null.
     */
    public AccountDto mapEntityToDto(Account entity) {
        if (entity == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return AccountDto.builder()
                .name(entity.getName())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .build();
    }

    /**
     * Maps an AccountDto to an Account entity.
     *
     * @param entityDto The AccountDto to be mapped.
     * @return The corresponding Account entity.
     * @throws IllegalArgumentException if the provided entityDto is null.
     */
    public Account mapDtoToEntity(AccountDto entityDto) {
        if (entityDto == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return Account.builder()
                .name(entityDto.getName())
                .status(entityDto.getStatus() != null ? AccountStatus.valueOf(entityDto.getStatus()) : null)
                .build();
    }
}

package com.project.bankapp.dto.mapper.account;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountStatus;
import org.springframework.stereotype.Component;

@Component
public class AccountUpdateMapper {
    public AccountDto mapEntityToDto(Account entity) {
        if (entity == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return AccountDto.builder()
                .name(entity.getName())
                .status(entity.getStatus() != null ? entity.getStatus().name() : null)
                .build();
    }
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

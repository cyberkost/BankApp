package com.project.bankapp.service;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
    void create(AccountDto accountDto);

    void create(AccountDto accountDto, String clientUuid);

    AccountDto findDtoById(String uuid);

    Account findById(UUID uuid);

    List<AccountDto> findAllNotDeleted();

    List<AccountDto> findDeletedAccounts();

    List<AccountDto> findAllByStatus(String status);

    List<AccountDto> findAllDtoByClientId(String clientUuid);

    void updateAccountDto(String uuid, AccountDto updatedAccountDto);

    void update(UUID uuid, Account account);

    void delete(String uuid);
}

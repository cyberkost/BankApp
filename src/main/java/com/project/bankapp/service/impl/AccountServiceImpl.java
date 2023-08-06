package com.project.bankapp.service.impl;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.dto.mapper.account.AccountCreationMapper;
import com.project.bankapp.dto.mapper.account.AccountUpdateMapper;
import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountStatus;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.dto.mapper.account.AccountDtoMapper;
import com.project.bankapp.repository.AccountRepository;
import com.project.bankapp.service.AccountService;
import com.project.bankapp.utils.updater.AccountUpdater;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountDtoMapper accountDtoMapper;
    private final AccountCreationMapper accountCreationMapper;
    private final AccountUpdateMapper accountUpdateMapper;
    private final AccountUpdater accountUpdater;

    @Override
    @Transactional
    public void create(AccountDto accountDto) {
        if (accountDto == null) {
            throw new IllegalArgumentException();
        }
        Account account = accountDtoMapper.mapDtoToEntity(accountDto);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void create(AccountDto accountDto, String uuid) {
        if (uuid == null || accountDto == null) {
            throw new IllegalArgumentException();
        }
        UUID clientUuid = UUID.fromString(uuid);
        Account account = accountCreationMapper.mapDtoToEntity(accountDto);
        account.setClientUuid(clientUuid);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public AccountDto findDtoById(String accountUuid) {
        if (accountUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(accountUuid);
        return accountDtoMapper.mapEntityToDto(accountRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid))));
    }

    @Override
    @Transactional
    public Account findById(UUID uuid) {
        if (uuid == null) {
            throw new IllegalArgumentException();
        }
        return accountRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
    }

    @Override
    @Transactional
    public List<AccountDto> findAllNotDeleted() {
        List<Account> accounts = accountRepository.findAllNotDeleted();
        return getDtoList(accounts);
    }

    @Override
    @Transactional
    public List<AccountDto> findDeletedAccounts() {
        List<Account> accounts = accountRepository.findAllDeleted();
        return getDtoList(accounts);
    }

    @Override
    @Transactional
    public List<AccountDto> findAllByStatus(String status) {
        List<Account> accounts = accountRepository.findAccountsByStatus(AccountStatus.valueOf(status));
        return getDtoList(accounts);
    }

    @Override
    @Transactional
    public void updateAccountDto(String existingUuid, AccountDto updatedAccountDto) {
        if (existingUuid == null || updatedAccountDto == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(existingUuid);
        Account updatedAccount = accountUpdateMapper.mapDtoToEntity(updatedAccountDto);
        Account account = accountRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        account = accountUpdater.update(account, updatedAccount);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void update(UUID uuid, Account updatedAccount) {
        if (uuid == null || updatedAccount == null) {
            throw new IllegalArgumentException();
        }
        Account account = accountRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        account = accountUpdater.update(account, updatedAccount);
        accountRepository.save(account);
    }

    @Override
    @Transactional
    public void delete(String accountUuid) {
        if (accountUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(accountUuid);
        Account account = accountRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        account.setDeleted(true);
        accountRepository.save(account);
    }

    public List<AccountDto> getDtoList(List<Account> accountList) {
        return Optional.ofNullable(accountList)
                .orElseThrow(() -> new DataNotFoundException("list is null"))
                .stream()
                .filter(Objects::nonNull)
                .map(accountDtoMapper::mapEntityToDto)
                .toList();
    }
}

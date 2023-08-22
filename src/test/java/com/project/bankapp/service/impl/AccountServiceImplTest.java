package com.project.bankapp.service.impl;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.dto.mapper.account.AccountCreationMapper;
import com.project.bankapp.dto.mapper.account.AccountDtoMapper;
import com.project.bankapp.dto.mapper.account.AccountUpdateMapper;
import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountStatus;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.repository.AccountRepository;
import com.project.bankapp.utils.updater.AccountUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private AccountDtoMapper accountDtoMapper;

    @Mock
    private AccountCreationMapper accountCreationMapper;

    @Mock
    private AccountUpdateMapper accountUpdateMapper;

    @Mock
    private AccountUpdater accountUpdater;

    @InjectMocks
    private AccountServiceImpl accountService;

    Account account1;
    Account account2;
    AccountDto accountDto1;
    AccountDto accountDto2;
    UUID clientUuid;
    UUID uuid;

    @BeforeEach
    void init() {
        account1 = new Account();
        account2 = new Account();
        clientUuid = UUID.randomUUID();
        uuid = UUID.randomUUID();
        accountDto1 = AccountDto.builder().build();
        accountDto2 = AccountDto.builder().build();
    }

    @Test
    void createValidAccountDtoShouldSaveAccount() {
        when(accountDtoMapper.mapDtoToEntity(accountDto1)).thenReturn(account1);
        accountService.create(accountDto1);
        verify(accountRepository, times(1)).save(account1);
    }

    @Test
    void createWithClientUuidShouldSaveAccountWithClientUuid() {
        String clientUuid = UUID.randomUUID().toString();
        when(accountCreationMapper.mapDtoToEntity(accountDto1)).thenReturn(account1);
        accountService.create(accountDto1, clientUuid);
        verify(accountRepository, times(1)).save(account1);
        assertEquals(account1.getClientUuid(), UUID.fromString(clientUuid));
        assertEquals(account1.getBalance(), BigDecimal.ZERO);
        assertEquals(account1.getStatus(), AccountStatus.PENDING);
    }

    @Test
    void createNullAccountDtoThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.create(null));
    }


    @Test
    void createNullClientUuidThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.create(accountDto1, null));
    }

    @Test
    void findByIdValidIdAccountFound() {
        UUID uuid = UUID.randomUUID();
        Account account = new Account();
        when(accountRepository.findById(uuid)).thenReturn(java.util.Optional.of(account));
        Account result = accountService.findById(uuid);
        assertNotNull(result);
    }

    @Test
    void findByIdInvalidUuidThrowsException() {
        String invalidUuid = "d358838e-1134-4101-85ac-5d99e8debfae";
        when(accountRepository.findById(UUID.fromString(invalidUuid))).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> accountService.findDtoById(invalidUuid));
        verify(accountRepository).findById(UUID.fromString(invalidUuid));
    }

    @Test
    void findByIdNullIdThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.findById(null));
    }

    @Test
    void findAllNotDeletedNoAccountsEmptyListReturned() {
        when(accountRepository.findAllNotDeleted()).thenReturn(Collections.emptyList());
        List<AccountDto> result = accountService.findAllNotDeleted();
        assertTrue(result.isEmpty());
    }

    @Test
    void findDtoByIdInvalidUuidThrowsIllegalArgumentException() {
        String invalidUuid = "invalid_uuid";
        assertThrows(IllegalArgumentException.class, () -> accountService.findDtoById(invalidUuid));
    }

    @Test
    void findDtoByIdNullUuidThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.findDtoById(null));
    }

    @Test
    void findDeletedAccounts() {
        List<Account> accounts = List.of(account1, account2);
        List<AccountDto> expected = List.of(accountDto1, accountDto2);
        when(accountRepository.findAllDeleted()).thenReturn(accounts);
        when(accountDtoMapper.mapEntityToDto(account1)).thenReturn(accountDto1);
        when(accountDtoMapper.mapEntityToDto(account2)).thenReturn(accountDto2);
        List<AccountDto> actual = accountService.findDeletedAccounts();
        assertEquals(expected, actual);
        verify(accountRepository).findAllDeleted();
        verify(accountDtoMapper, times(2)).mapEntityToDto(any(Account.class));
    }

    @Test
    void findAllByStatusSuccess() {
        List<AccountDto> expected = List.of(accountDto1, accountDto2);
        List<Account> accounts = List.of(account1, account2);
        String status = "ACTIVE";
        when(accountRepository.findAccountsByStatus(AccountStatus.valueOf(status))).thenReturn(accounts);
        when(accountDtoMapper.mapEntityToDto(account1)).thenReturn(accountDto1);
        when(accountDtoMapper.mapEntityToDto(account2)).thenReturn(accountDto2);
        List<AccountDto> actual = accountService.findAllByStatus(status);
        assertEquals(expected, actual);
        verify(accountRepository).findAccountsByStatus(AccountStatus.valueOf(status));
        verify(accountDtoMapper, times(2)).mapEntityToDto(any(Account.class));
        verifyNoMoreInteractions(accountRepository);
    }

    @Test
    void updateAccountDtoValidAccountSuccess() {
        AccountDto updatedAccountDto = accountDto1;
        Account updatedAccount = account1;
        Account account = account1;
        when(accountUpdateMapper.mapDtoToEntity(updatedAccountDto)).thenReturn(updatedAccount);
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account));
        when(accountUpdater.update(account, updatedAccount)).thenReturn(updatedAccount);
        accountService.updateAccountDto(String.valueOf(uuid), updatedAccountDto);
        verify(accountUpdateMapper).mapDtoToEntity(updatedAccountDto);
        verify(accountRepository).findById(uuid);
        verify(accountUpdater).update(account, updatedAccount);
        verify(accountRepository).save(account);
    }

    @Test
    void updateNullAccountThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.update(uuid, null));
    }

    @Test
    void updateNullUuidThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> accountService.update(null, account1));
    }

    @Test
    void deleteUserFromUserRepositorySuccess() {
        when(accountRepository.findById(uuid)).thenReturn(Optional.of(account1));
        accountService.delete(String.valueOf(uuid));
        verify(accountRepository).findById(uuid);
        verify(accountRepository).save(account1);
        assertTrue(account1.isDeleted());
    }

    @Test
    void findAllDtoByClientIdSuccess() {
        List<Account> accounts = List.of(account1, account2);
        List<AccountDto> expected = List.of(accountDto1, accountDto2);
        when(accountRepository.findAccountsByClientUuid(clientUuid)).thenReturn(accounts);
        when(accountDtoMapper.mapEntityToDto(account1)).thenReturn(accountDto1);
        when(accountDtoMapper.mapEntityToDto(account2)).thenReturn(accountDto2);
        List<AccountDto> actual = accountService.findAllDtoByClientId(String.valueOf(clientUuid));
        assertEquals(expected, actual);
        verify(accountRepository).findAccountsByClientUuid(clientUuid);
        verify(accountDtoMapper, times(2)).mapEntityToDto(any(Account.class));
    }
}
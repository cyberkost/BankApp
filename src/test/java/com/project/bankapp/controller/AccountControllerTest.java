package com.project.bankapp.controller;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.enums.AccountStatus;
import com.project.bankapp.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {
    @Mock
    AccountService accountService;
    @InjectMocks
    AccountController accountController;
    String uuid;

    @BeforeEach
    void setUp() {
        uuid = "7bcf30be-8c6e-4e10-a73b-706849fc94dc";
    }

    @Test
    void createAccount_success() {
        // when
        AccountDto accountDto = AccountDto.builder().build();
        AccountDto createdAccountDto = AccountDto.builder().build();
        // when
        ResponseEntity<AccountDto> actual = accountController.createAccount(accountDto);
        // then
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(createdAccountDto, actual.getBody());
        verify(accountService).create(accountDto);
    }

    @Test
    void createAccount_emptyAccountDto_savesNoData() {
        // when
        ResponseEntity<AccountDto> actual = accountController.createAccount(null);
        // then
        assertNull(actual.getBody());
        verify(accountService, never()).create(any(AccountDto.class));
    }

    @Test
    void createAccount_withClientUuid_success() {
        // when
        AccountDto accountDto = AccountDto.builder().build();
        AccountDto createdAccountDto = AccountDto.builder().build();
        // when
        ResponseEntity<AccountDto> actual = accountController.createAccount(accountDto, uuid);
        // then
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(createdAccountDto, actual.getBody());
        verify(accountService).create(accountDto, uuid);
    }

    @Test
    void findAllAccounts_success() {
        // given
        List<AccountDto> expected = List.of(AccountDto.builder().build(), AccountDto.builder().build());
        when(accountService.findAllNotDeleted()).thenReturn(expected);
        // when
        ResponseEntity<List<AccountDto>> actual = accountController.findAllAccounts();
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(accountService).findAllNotDeleted();
    }

    @Test
    void findAllAccounts_withEmptyList_returnsNoContentStatus() {
        // given
        List<AccountDto> expected = Collections.emptyList();
        when(accountService.findAllNotDeleted()).thenReturn(expected);
        // when
        ResponseEntity<List<AccountDto>> actual = accountController.findAllAccounts();
        // then
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertNull(actual.getBody());
        verify(accountService).findAllNotDeleted();
    }

    @Test
    void findAccountByUuid_success() {
        // given
        AccountDto expected = AccountDto.builder().build();
        when(accountService.findDtoById(uuid)).thenReturn(expected);
        // when
        ResponseEntity<AccountDto> actual = accountController.findAccountByUuid(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(accountService).findDtoById(uuid);
    }

    @Test
    void findAllAccountsByStatus_success() {
        // given
        List<AccountDto> expected = List.of(AccountDto.builder().build(), AccountDto.builder().build());
        String status = AccountStatus.ACTIVE.name();
        when(accountService.findAllByStatus(status)).thenReturn(expected);
        // when
        ResponseEntity<List<AccountDto>> actual = accountController.findAllAccountsByStatus(status);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(accountService).findAllByStatus(status);
    }

    @Test
    void updateAccountDto_success() {
        // given
        AccountDto expected = AccountDto.builder().build();
        // when
        ResponseEntity<AccountDto> actual = accountController.updateAccount(uuid, expected);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(accountService).updateAccountDto(uuid, expected);
    }

    @Test
    void deleteAccount_success() {
        // when
        ResponseEntity<String> actual = accountController.deleteAccount(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(accountService).delete(uuid);
    }
}
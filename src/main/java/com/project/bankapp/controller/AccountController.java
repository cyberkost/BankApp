package com.project.bankapp.controller;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;
import com.project.bankapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/account/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        log.info("endpoint request: create new account");
        accountService.create(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @PostMapping(value = "/account/create/with-client-id/{uuid}")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto, @PathVariable String uuid) {
        log.info("endpoint request: create account");
        accountService.create(accountDto, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @GetMapping(value = "/account/find/all")
    public ResponseEntity<List<AccountDto>> findAllAccounts() {
        log.info("endpoint request: find all accounts");
        List<AccountDto> accountDtoList = accountService.findAllNotDeleted();
        return accountDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accountDtoList);
    }

    @GetMapping(value = "/account/find/{uuid}")
    public ResponseEntity<AccountDto> findAccountByUuid(@PathVariable String uuid) {
        log.info("endpoint request: find account by uuid {}", uuid);
        AccountDto accountDto = accountService.findDtoById(uuid);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping(value = "/account/find/all/by-status/{status}")
    public ResponseEntity<List<AccountDto>> findAllAccountsByStatus(@PathVariable String status) {
        log.info("endpoint request: find all accounts by status {}", status);
        List<AccountDto> accountDtoList = accountService.findAllByStatus(status);
        return accountDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accountDtoList);
    }

    @GetMapping(value = "/account/find/all/by-client-id/{uuid}")
    public ResponseEntity<List<AccountDto>> findAllAccountsByClientUuid(@PathVariable String uuid) {
        log.info("endpoint request: find all accounts by client id {} ", uuid);
        List<AccountDto> accountDtoList = accountService.findAllDtoByClientId(uuid);
        return accountDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accountDtoList);
    }

    @PutMapping(value = "/account/update/{uuid}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String uuid, @RequestBody AccountDto updatedAccountDto) {
        log.info("endpoint request: update account uuid {}", uuid);
        accountService.updateAccountDto(uuid, updatedAccountDto);
        return ResponseEntity.ok(updatedAccountDto);
    }

    @DeleteMapping(value = "/account/delete/{uuid}")
    public ResponseEntity<String> deleteAccount(@PathVariable String uuid) {
        log.info("endpoint request: delete account uuid {}", uuid);
        accountService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}

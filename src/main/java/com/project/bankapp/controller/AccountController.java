package com.project.bankapp.controller;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;
import com.project.bankapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping(value = "/account/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        accountService.create(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @PostMapping(value = "/account/create/with-client-id/{uuid}")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto, @PathVariable String uuid) {
        accountService.create(accountDto, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    @GetMapping(value = "/account/find/all")
    public ResponseEntity<List<AccountDto>> findAllAccounts() {
        List<AccountDto> accountDtoList = accountService.findAllNotDeleted();
        return accountDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accountDtoList);
    }

    @GetMapping(value = "/account/find/{uuid}")
    public ResponseEntity<AccountDto> findAccountByUuid(@PathVariable String uuid) {
        AccountDto accountDto = accountService.findDtoById(uuid);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping(value = "/account/find/all/by-status/{status}")
    public ResponseEntity<List<AccountDto>> findAllAccountsByStatus(@PathVariable String status) {
        List<AccountDto> accountDtoList = accountService.findAllByStatus(status);
        return accountDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accountDtoList);
    }

    @PutMapping(value = "/account/update/{uuid}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String uuid, @RequestBody AccountDto updatedAccountDto) {
        accountService.updateAccountDto(uuid, updatedAccountDto);
        return ResponseEntity.ok(updatedAccountDto);
    }

    @DeleteMapping(value = "/account/delete/{uuid}")
    public ResponseEntity<String> deleteAccount(@PathVariable String uuid) {
        accountService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}

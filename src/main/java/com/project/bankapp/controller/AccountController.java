package com.project.bankapp.controller;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling account-related operations.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountController {

    private final AccountService accountService;

    /**
     * Creates a new account.
     *
     * @param accountDto The DTO containing account information to be created.
     * @return A ResponseEntity with the created AccountDto.
     */
    @PostMapping(value = "/account/create")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto) {
        log.info("endpoint request: create new account");
        accountService.create(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    /**
     * Creates a new account associated with a specific client.
     *
     * @param accountDto The DTO containing account information to be created.
     * @param uuid       The UUID of the client to associate the account with.
     * @return A ResponseEntity with the created AccountDto.
     */
    @PostMapping(value = "/account/create/with-client-id/{uuid}")
    public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto accountDto, @PathVariable String uuid) {
        log.info("endpoint request: create account");
        accountService.create(accountDto, uuid);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDto);
    }

    /**
     * Retrieves a list of all accounts.
     *
     * @return A ResponseEntity with a list of AccountDto objects, or no content if the list is empty.
     */
    @GetMapping(value = "/account/find/all")
    public ResponseEntity<List<AccountDto>> findAllAccounts() {
        log.info("endpoint request: find all accounts");
        List<AccountDto> accountDtoList = accountService.findAllNotDeleted();
        return accountDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accountDtoList);
    }

    /**
     * Retrieves an account by its UUID.
     *
     * @param uuid The UUID of the account to retrieve.
     * @return A ResponseEntity with the retrieved AccountDto.
     */
    @GetMapping(value = "/account/find/{uuid}")
    public ResponseEntity<AccountDto> findAccountByUuid(@PathVariable String uuid) {
        log.info("endpoint request: find account by uuid {}", uuid);
        AccountDto accountDto = accountService.findDtoById(uuid);
        return ResponseEntity.ok(accountDto);
    }

    /**
     * Retrieves a list of accounts based on their status.
     *
     * @param status The status of the accounts to retrieve.
     * @return A ResponseEntity with a list of AccountDto objects, or no content if the list is empty.
     */
    @GetMapping(value = "/account/find/all/by-status/{status}")
    public ResponseEntity<List<AccountDto>> findAllAccountsByStatus(@PathVariable String status) {
        log.info("endpoint request: find all accounts by status {}", status);
        List<AccountDto> accountDtoList = accountService.findAllByStatus(status);
        return accountDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accountDtoList);
    }

    /**
     * Retrieves a list of accounts associated with a specific client.
     *
     * @param uuid The UUID of the client to retrieve accounts for.
     * @return A ResponseEntity with a list of AccountDto objects, or no content if the list is empty.
     */
    @GetMapping(value = "/account/find/all/by-client-id/{uuid}")
    public ResponseEntity<List<AccountDto>> findAllAccountsByClientUuid(@PathVariable String uuid) {
        log.info("endpoint request: find all accounts by client id {} ", uuid);
        List<AccountDto> accountDtoList = accountService.findAllDtoByClientId(uuid);
        return accountDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(accountDtoList);
    }

    /**
     * Updates an existing account.
     *
     * @param uuid              The UUID of the account to update.
     * @param updatedAccountDto The updated AccountDto with new information.
     * @return A ResponseEntity with the updated AccountDto.
     */
    @PutMapping(value = "/account/update/{uuid}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable String uuid, @RequestBody AccountDto updatedAccountDto) {
        log.info("endpoint request: update account uuid {}", uuid);
        accountService.updateAccountDto(uuid, updatedAccountDto);
        return ResponseEntity.ok(updatedAccountDto);
    }

    /**
     * Deletes an account by its UUID.
     *
     * @param uuid The UUID of the account to delete.
     * @return A ResponseEntity indicating successful deletion.
     */
    @DeleteMapping(value = "/account/delete/{uuid}")
    public ResponseEntity<String> deleteAccount(@PathVariable String uuid) {
        log.info("endpoint request: delete account uuid {}", uuid);
        accountService.delete(uuid);
        return ResponseEntity.ok().build();
    }
}

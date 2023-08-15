package com.project.bankapp.service;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.entity.Account;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing account-related operations.
 */
public interface AccountService {
    /**
     * Creates a new account based on the provided account DTO.
     *
     * @param accountDto The DTO containing account information.
     */
    void create(AccountDto accountDto);

    /**
     * Creates a new account associated with the specified client UUID based on the provided account DTO.
     *
     * @param accountDto The DTO containing account information.
     * @param clientUuid The UUID of the client associated with the account.
     */
    void create(AccountDto accountDto, String clientUuid);

    /**
     * Retrieves an account DTO by its UUID.
     *
     * @param uuid The UUID of the account.
     * @return The DTO representing the account.
     */
    AccountDto findDtoById(String uuid);

    /**
     * Retrieves an account entity by its UUID.
     *
     * @param uuid The UUID of the account.
     * @return The account entity.
     */
    Account findById(UUID uuid);

    /**
     * Retrieves a list of all non-deleted account DTOs.
     *
     * @return A list of account DTOs.
     */
    List<AccountDto> findAllNotDeleted();

    /**
     * Retrieves a list of all deleted account DTOs.
     *
     * @return A list of deleted account DTOs.
     */
    List<AccountDto> findDeletedAccounts();

    /**
     * Retrieves a list of account DTOs based on the specified status.
     *
     * @param status The status of the accounts to be retrieved.
     * @return A list of account DTOs matching the given status.
     */
    List<AccountDto> findAllByStatus(String status);

    /**
     * Retrieves a list of account DTOs associated with the specified client UUID.
     *
     * @param clientUuid The UUID of the client.
     * @return A list of account DTOs associated with the client.
     */
    List<AccountDto> findAllDtoByClientId(String clientUuid);

    /**
     * Updates an account DTO with the provided updated account DTO based on its UUID.
     *
     * @param uuid              The UUID of the account to be updated.
     * @param updatedAccountDto The updated account DTO.
     */
    void updateAccountDto(String uuid, AccountDto updatedAccountDto);

    /**
     * Updates an account entity with the provided account entity based on its UUID.
     *
     * @param uuid    The UUID of the account to be updated.
     * @param account The updated account entity.
     */
    void update(UUID uuid, Account account);

    /**
     * Deletes an account based on its UUID.
     *
     * @param uuid The UUID of the account to be deleted.
     */
    void delete(String uuid);
}

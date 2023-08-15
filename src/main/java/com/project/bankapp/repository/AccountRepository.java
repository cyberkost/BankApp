package com.project.bankapp.repository;

import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing account data in the database.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {
    /**
     * Retrieves a list of all non-deleted accounts.
     *
     * @return A list of non-deleted accounts.
     */
    @Query("SELECT ac FROM Account ac WHERE ac.isDeleted = false")
    List<Account> findAllNotDeleted();

    /**
     * Retrieves a list of all deleted accounts.
     *
     * @return A list of deleted accounts.
     */
    @Query("SELECT ac FROM Account ac WHERE ac.isDeleted = true")
    List<Account> findAllDeleted();

    /**
     * Retrieves a list of accounts with the specified status.
     *
     * @param status The status of the accounts to retrieve.
     * @return A list of accounts with the specified status.
     */
    List<Account> findAccountsByStatus(AccountStatus status);

    /**
     * Retrieves a list of accounts associated with the specified client UUID.
     *
     * @param uuid The UUID of the client for which to retrieve accounts.
     * @return A list of accounts associated with the specified client UUID.
     */
    List<Account> findAccountsByClientUuid(UUID uuid);
}

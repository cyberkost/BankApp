package com.project.bankapp.repository;

import com.project.bankapp.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for accessing and managing transactions in the database.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    /**
     * Retrieves a list of transactions associated with the given debit account UUID.
     *
     * @param uuid The UUID of the debit account.
     * @return A list of transactions associated with the debit account.
     */
    List<Transaction> findTransactionsByDebitAccountUuid(UUID uuid);

    /**
     * Retrieves a list of transactions associated with the given credit account UUID.
     *
     * @param uuid The UUID of the credit account.
     * @return A list of transactions associated with the credit account.
     */
    List<Transaction> findTransactionsByCreditAccountUuid(UUID uuid);

    /**
     * Retrieves a list of transactions involving accounts associated with the specified client UUID.
     *
     * @param clientUuid The UUID of the client.
     * @return A list of transactions involving accounts associated with the client.
     */
    @Query("SELECT tr FROM Transaction tr " +
            "JOIN Account ac ON ac.uuid = tr.debitAccountUuid " +
            "OR ac.uuid = tr.creditAccountUuid " +
            "JOIN Client cl ON cl.uuid = ac.clientUuid " +
            "WHERE cl.uuid = :clientUuid")
    List<Transaction> findAllTransactionsWhereClientIdIs(@Param("clientUuid") UUID clientUuid);
}

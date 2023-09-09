package com.project.bankapp.service;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.entity.Transaction;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service interface for managing transaction-related operations.
 */
public interface TransactionService {
    /**
     * Creates a new transaction based on the provided transaction data.
     *
     * @param transactionDto The DTO containing transaction information.
     */
    void create(TransactionDto transactionDto);

    /**
     * Retrieves a list of all transactions.
     *
     * @return A list of all transactions.
     */
    List<TransactionDto> findAll();

    /**
     * Retrieves a transaction by its unique identifier.
     *
     * @param uuid The unique identifier of the transaction.
     * @return The DTO representing the found transaction.
     */
    TransactionDto findById(String uuid);

    /**
     * Retrieves a list of outgoing transactions associated with the provided account UUID.
     *
     * @param uuid The UUID of the account.
     * @return A list of outgoing transactions associated with the account.
     */
    List<TransactionDto> findOutgoingTransactions(String uuid);

    /**
     * Retrieves a list of incoming transactions associated with the provided account UUID.
     *
     * @param uuid The UUID of the account.
     * @return A list of incoming transactions associated with the account.
     */
    List<TransactionDto> findIncomingTransactions(String uuid);

    /**
     * Retrieves a list of transactions associated with the provided client UUID.
     *
     * @param uuid The UUID of the client.
     * @return A list of transactions associated with the client.
     */
    List<TransactionDto> findAllTransactionsByClientId(String uuid);

    /**
     * Retrieves a list of transaction data transfer objects (DTOs) for a given username.
     *
     * @param userName The username of the user for whom transactions should be retrieved.
     * @return A List of TransactionDto objects representing the transactions associated with the specified username.
     * If no transactions are found, an empty List is returned.
     */
    List<TransactionDto> findAllByUsername(String userName);

    /**
     * Performs a funds transfer based on the provided transaction data.
     *
     * @param transactionDto The DTO containing transaction information for the transfer.
     */
    void transferFunds(TransactionDto transactionDto);

}
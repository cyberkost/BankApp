package com.project.bankapp.controller;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class responsible for handling transaction-related.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    /**
     * Creates a new transaction.
     *
     * @param transactionDto The DTO containing transaction information to be created.
     * @return A ResponseEntity with the created TransactionDto.
     */
    @PostMapping(value = "/transaction/create")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        log.info("endpoint request: create transaction");
        transactionService.create(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
    }

    /**
     * Retrieves a list of all transactions.
     *
     * @return A ResponseEntity with a list of TransactionDto objects, or no content if the list is empty.
     */
    @GetMapping(value = "/transaction/find-all")
    public ResponseEntity<List<TransactionDto>> findAllTransactions() {
        log.info("endpoint request: find all transactions");
        List<TransactionDto> transactionList = transactionService.findAll();
        return transactionList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactionList);
    }

    /**
     * Retrieves a transaction by its UUID.
     *
     * @param uuid The UUID of the transaction to retrieve.
     * @return A ResponseEntity with the retrieved TransactionDto.
     */
    @GetMapping(value = "/transaction/find/{uuid}")
    public ResponseEntity<TransactionDto> findTransactionByUuid(@PathVariable String uuid) {
        log.info("endpoint request: find transaction by uuid {}", uuid);
        TransactionDto transactionDto = transactionService.findById(uuid);
        return ResponseEntity.ok(transactionDto);
    }

    /**
     * Retrieves outgoing transactions for a specific account.
     *
     * @param uuid The UUID of the account to retrieve outgoing transactions for.
     * @return A ResponseEntity with a list of TransactionDto objects, or no content if the list is empty.
     */
    @GetMapping(value = "/transaction/find/outgoing/{uuid}")
    public ResponseEntity<List<TransactionDto>> findOutgoingTransactions(@PathVariable String uuid) {
        log.info("endpoint request: find outgoing transactions by uuid {}", uuid);
        List<TransactionDto> transactionList = transactionService.findOutgoingTransactions(uuid);
        return transactionList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactionList);
    }

    /**
     * Retrieves incoming transactions for a specific account.
     *
     * @param uuid The UUID of the account to retrieve incoming transactions for.
     * @return A ResponseEntity with a list of TransactionDto objects, or no content if the list is empty.
     */
    @GetMapping(value = "/transaction/find/incoming/{uuid}")
    public ResponseEntity<List<TransactionDto>> findIncomingTransactions(@PathVariable String uuid) {
        log.info("endpoint request: find incoming transaction by uuid {}", uuid);
        List<TransactionDto> transactionList = transactionService.findIncomingTransactions(uuid);
        return transactionList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactionList);
    }

    /**
     * Retrieves a list of transactions associated with a specific client.
     *
     * @param uuid The unique identifier of the client.
     * @return ResponseEntity containing a list of TransactionDto objects if found, or a no-content response if empty.
     */
    @GetMapping(value = "/transaction/find/all-by-client/{uuid}")
    public ResponseEntity<List<TransactionDto>> findAllTransactionsByClient(@PathVariable String uuid) {
        log.info("endpoint request: find all transactions by client id {}", uuid);
        List<TransactionDto> transactionDtoList = transactionService.findAllTransactionsByClientId(uuid);
        return transactionDtoList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactionDtoList);
    }

    /**
     * Transfers funds between accounts.
     *
     * @param transactionDto The DTO containing transaction information for the funds transfer.
     * @return A ResponseEntity indicating successful funds transfer.
     */
    @PostMapping(value = "/transaction/transfer")
    public ResponseEntity<String> transferFunds(@RequestBody TransactionDto transactionDto) {
        log.info("endpoint request: execute money transfer");
        transactionService.transferFunds(transactionDto);
        return ResponseEntity.ok().build();
    }
}

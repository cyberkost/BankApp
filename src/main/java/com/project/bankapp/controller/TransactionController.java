package com.project.bankapp.controller;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(value = "/transaction/create")
    public ResponseEntity<TransactionDto> createTransaction(@RequestBody TransactionDto transactionDto) {
        log.info("endpoint request: create transaction");
        transactionService.create(transactionDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDto);
    }

    @GetMapping(value = "/transaction/find-all")
    public ResponseEntity<List<TransactionDto>> findAllTransactions() {
        log.info("endpoint request: find all transactions");
        List<TransactionDto> transactionList = transactionService.findAll();
        return transactionList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactionList);
    }

    @GetMapping(value = "/transaction/find/{uuid}")
    public ResponseEntity<TransactionDto> findTransactionByUuid(@PathVariable String uuid) {
        log.info("endpoint request: find transaction by uuid {}", uuid);
        TransactionDto transactionDto = transactionService.findById(uuid);
        return ResponseEntity.ok(transactionDto);
    }

    @GetMapping(value = "/transaction/find/outgoing/{uuid}")
    public ResponseEntity<List<TransactionDto>> findOutgoingTransactions(@PathVariable String uuid) {
        log.info("endpoint request: find outgoing transactions by uuid {}", uuid);
        List<TransactionDto> transactionList = transactionService.findOutgoingTransactions(uuid);
        return transactionList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactionList);
    }

    @GetMapping(value = "/transaction/find/incoming/{uuid}")
    public ResponseEntity<List<TransactionDto>> findIncomingTransactions(@PathVariable String uuid) {
        log.info("endpoint request: find incoming transaction by uuid {}", uuid);
        List<TransactionDto> transactionList = transactionService.findIncomingTransactions(uuid);
        return transactionList.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(transactionList);
    }
}

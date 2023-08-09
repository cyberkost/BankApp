package com.project.bankapp.service.impl;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.dto.mapper.transaction.TransactionDtoMapper;
import com.project.bankapp.entity.Transaction;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.repository.TransactionRepository;
import com.project.bankapp.service.AccountService;
import com.project.bankapp.service.ClientService;
import com.project.bankapp.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDtoMapper transactionDtoMapper;
    private final AccountService accountService;
    private final ClientService clientService;

    @Override
    @Transactional
    public void create(TransactionDto transactionDto) {
        Transaction transaction = transactionDtoMapper.mapDtoToEntity(transactionDto);
        transactionRepository.save(transaction);
        log.info("transaction created");
    }

    @Override
    @Transactional
    public List<TransactionDto> findAll() {
        log.info("retrieving list of transactions");
        List<Transaction> transactions = transactionRepository.findAll();
        return getDtoList(transactions);
    }

    @Override
    @Transactional
    public TransactionDto findById(String transactionUuid) {
        if (transactionUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(transactionUuid);
        log.info("retrieving transaction by id {}", uuid);
        return transactionDtoMapper.mapEntityToDto(transactionRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid))));
    }

    @Override
    @Transactional
    public List<TransactionDto> findOutgoingTransactions(String senderUuid) {
        if (senderUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(senderUuid);
        log.info("retrieving list of transactions by sender id {}", uuid);
        List<Transaction> transactions = transactionRepository.findTransactionsByDebitAccountUuid(uuid);
        return getDtoList(transactions);
    }

    @Override
    @Transactional
    public List<TransactionDto> findIncomingTransactions(String recipientUuid) {
        if (recipientUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(recipientUuid);
        log.info("retrieving list of transactions by recipient id {}", uuid);
        List<Transaction> transactions = transactionRepository.findTransactionsByCreditAccountUuid(uuid);
        return getDtoList(transactions);
    }

    @Override
    @Transactional
    public List<TransactionDto> findAllTransactionsByClientId(String clientUuid) {
        if (clientUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(clientUuid);
        log.info("retrieving list of transactions by client id {}", uuid);
        List<Transaction> transactions = transactionRepository.findAllTransactionsWhereClientIdIs(uuid);
        return getDtoList(transactions);
    }

//    @Override
//    @Transactional
//    public void transferFunds(Transaction transaction) {
//
//    }

    private List<TransactionDto> getDtoList(List<Transaction> transactions) {
        return Optional.ofNullable(transactions)
                .orElse(Collections.emptyList())
                .stream()
                .map(transactionDtoMapper::mapEntityToDto)
                .toList();
    }
}

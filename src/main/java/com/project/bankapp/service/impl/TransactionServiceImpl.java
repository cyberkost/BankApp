package com.project.bankapp.service.impl;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.dto.mapper.transaction.TransactionDtoMapper;
import com.project.bankapp.entity.Transaction;
import com.project.bankapp.repository.TransactionRepository;
import com.project.bankapp.service.AccountService;
import com.project.bankapp.service.ClientService;
import com.project.bankapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDtoMapper transactionDtoMapper;
    private final AccountService accountService;
    private final ClientService clientService;

    @Override
    public void create(TransactionDto transactionDto) {

    }

    @Override
    public List<TransactionDto> findAll() {
        return null;
    }

    @Override
    public TransactionDto findById(String uuid) {
        return null;
    }

    @Override
    public List<TransactionDto> findOutgoingTransactions(String uuid) {
        return null;
    }

    @Override
    public List<TransactionDto> findIncomingTransactions(String uuid) {
        return null;
    }

    @Override
    public List<TransactionDto> findAllTransactionsByClientId(String uuid) {
        return null;
    }

    @Override
    public void transferFunds(Transaction transaction) {

    }
}

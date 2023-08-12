package com.project.bankapp.service;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.entity.Transaction;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TransactionService {
    void create(TransactionDto transactionDto);

    List<TransactionDto> findAll();

    TransactionDto findById(String uuid);

    List<TransactionDto> findOutgoingTransactions(String uuid);

    List<TransactionDto> findIncomingTransactions(String uuid);

    List<TransactionDto> findAllTransactionsByClientId(String uuid);

    void transferFunds(Transaction transaction);


}

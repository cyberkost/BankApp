package com.project.bankapp.service.impl;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.dto.mapper.transaction.TransactionDtoMapper;
import com.project.bankapp.entity.Account;
import com.project.bankapp.entity.Transaction;
import com.project.bankapp.entity.enums.AccountStatus;
import com.project.bankapp.entity.enums.CurrencyCode;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.exception.InsufficientFundsException;
import com.project.bankapp.exception.TransactionNotAllowedException;
import com.project.bankapp.repository.TransactionRepository;
import com.project.bankapp.service.AccountService;
import com.project.bankapp.service.ClientService;
import com.project.bankapp.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Override
    @Transactional
    public void transferFunds(TransactionDto transactionDto) {
        Transaction transaction = transactionDtoMapper.mapDtoToEntity(transactionDto);

        BigDecimal amount = transaction.getAmount();
        Account senderAccount = accountService.findById(transaction.getDebitAccountUuid());
        Account recipientAccount = accountService.findById(transaction.getCreditAccountUuid());

        checkAmount(amount);
        checkBalanceNotNull(senderAccount, recipientAccount);
        checkAccountStatusNotNull(senderAccount, recipientAccount);
        checkSufficientFunds(amount, senderAccount);
        checkAccountsStatusActive(senderAccount, recipientAccount);
//        checkClientsStatusActive(senderAccount, recipientAccount);

        CurrencyCode senderCurrency = senderAccount.getCurrencyCode();
        transaction.setCurrencyCode(senderCurrency);
        senderAccount.setBalance(senderAccount.getBalance().subtract(amount));

        performTransfer(transaction, amount, senderAccount, recipientAccount);
    }

    private void performTransfer(Transaction transaction, BigDecimal amount, Account sender, Account recipient) {
        CurrencyCode senderCurrency = sender.getCurrencyCode();
        CurrencyCode recipientCurrency = recipient.getCurrencyCode();
        if (recipientCurrency.equals(senderCurrency)) {
            recipient.setBalance(recipient.getBalance().add(amount));
        }
        accountService.update(sender.getUuid(), sender);
        accountService.update(recipient.getUuid(), recipient);
        transactionRepository.save(transaction);
        log.info("transfer saved to db");
    }

    private void checkAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount is less or equals Zero");
        }
    }

    private void checkBalanceNotNull(Account senderAccount, Account recipientAccount) {
        if (senderAccount.getBalance() == null || recipientAccount.getBalance() == null) {
            throw new IllegalArgumentException("Balance is null");
        }
    }

    private void checkAccountStatusNotNull(Account senderAccount, Account recipientAccount) {
        if (senderAccount.getStatus() == null || recipientAccount.getStatus() == null) {
            throw new IllegalArgumentException("Status null");
        }
    }

    private void checkSufficientFunds(BigDecimal amount, Account senderAccount) {
        if (senderAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Sender balance is too low");
        }
    }

    private void checkAccountsStatusActive(Account senderAccount, Account recipientAccount) {
        AccountStatus status = AccountStatus.ACTIVE;
        if (!senderAccount.getStatus().equals(status) || !recipientAccount.getStatus().equals(status)) {
            throw new TransactionNotAllowedException("Account is not active");
        }
    }

    private void checkClientsStatusActive(Account senderAccount, Account recipientAccount) {
        boolean isSenderActive = clientService.isClientStatusActive(senderAccount.getClientUuid());
        log.info("sender active {}", isSenderActive);
        boolean isRecipientActive = clientService.isClientStatusActive(recipientAccount.getClientUuid());
        log.info("recipient active {}", isRecipientActive);
        if (!isSenderActive || !isRecipientActive) {
            throw new TransactionNotAllowedException("Client is not active");
        }
    }

    private List<TransactionDto> getDtoList(List<Transaction> transactions) {
        return Optional.ofNullable(transactions)
                .orElse(Collections.emptyList())
                .stream()
                .map(transactionDtoMapper::mapEntityToDto)
                .toList();
    }
}

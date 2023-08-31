package com.project.bankapp.service.impl;

import com.project.bankapp.dto.AccountDto;
import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.dto.mapper.account.AccountDtoMapper;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    TransactionDtoMapper transactionDtoMapper;
    @Mock
    AccountService accountService;
    @Mock
    AccountDtoMapper accountDtoMapper;
    @Mock
    ClientService clientService;
    @InjectMocks
    TransactionServiceImpl transactionService;
    UUID uuid;
    String strUuid;
    Transaction transaction;
    Transaction transaction1;
    Transaction transaction2;
    TransactionDto transactionDto;
    TransactionDto transactionDto1;
    TransactionDto transactionDto2;
    List<Transaction> transactions;
    List<TransactionDto> expected;
    AccountDto senderAccountDto;
    AccountDto recipientAccountDto;
    Account sender;
    Account recipient;

    @BeforeEach
    void setUp() {
        uuid = UUID.fromString("f0621a3c-6849-4ef5-8fc2-7bf7dd450d26");
        strUuid = "f0621a3c-6849-4ef5-8fc2-7bf7dd450d26";

        transaction = new Transaction();
        transaction.setDebitAccountUuid(UUID.fromString("ed3a5e5a-cd77-4052-91fc-b042f2aa4dbe"));
        transaction.setCreditAccountUuid(UUID.fromString("b0e642b4-d957-4cee-b4ca-13839ad16a20"));

        transaction1 = new Transaction();
        transaction2 = new Transaction();

        sender = new Account();
        sender.setClientUuid(UUID.fromString("1989d4da-0f91-46d3-96c6-2b4a72950c89"));
        sender.setBalance(BigDecimal.valueOf(200));
        sender.setStatus(AccountStatus.ACTIVE);
        sender.setCurrencyCode(CurrencyCode.EUR);

        recipient = new Account();
        recipient.setClientUuid(UUID.fromString("7e3dc741-7e9a-4b60-9f96-da9fc0924927"));
        recipient.setBalance(BigDecimal.ZERO);
        recipient.setStatus(AccountStatus.ACTIVE);
        recipient.setCurrencyCode(CurrencyCode.EUR);

        transactionDto = TransactionDto.builder().build();
        transactionDto1 = TransactionDto.builder().build();
        transactionDto2 = TransactionDto.builder().build();
        transactions = List.of(transaction1, transaction2);
        expected = List.of(transactionDto1, transactionDto2);
        senderAccountDto = AccountDto.builder().build();
        recipientAccountDto = AccountDto.builder().build();
    }

    @Test
    void create_success() {
        // given
        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        // when
        transactionService.create(transactionDto);
        // then
        verify(transactionDtoMapper).mapDtoToEntity(transactionDto);
        verify(transactionRepository).save(transaction);
    }

    @Test
    void findAll_success() {
        // given
        when(transactionRepository.findAll()).thenReturn(transactions);
        when(transactionDtoMapper.mapEntityToDto(transaction1)).thenReturn(transactionDto1);
        when(transactionDtoMapper.mapEntityToDto(transaction2)).thenReturn(transactionDto2);
        // when
        List<TransactionDto> actual = transactionService.findAll();
        // then
        assertEquals(expected, actual);
        verify(transactionRepository).findAll();
        verify(transactionDtoMapper, times(2)).mapEntityToDto(any(Transaction.class));
    }

    @Test
    void findAll_withNull_emptyListReturned() {
        // given
        when(transactionRepository.findAll()).thenReturn(null);
        // when
        List<TransactionDto> actual = transactionService.findAll();
        //then
        assertTrue(actual.isEmpty());
    }

    @Test
    void findById_success() {
        // given
        TransactionDto expected = TransactionDto.builder().build();
        when(transactionRepository.findById(uuid)).thenReturn(Optional.ofNullable(transaction));
        when(transactionDtoMapper.mapEntityToDto(transaction)).thenReturn(expected);
        // when
        TransactionDto actual = transactionService.findById(String.valueOf(uuid));
        // then
        assertEquals(expected, actual);
        verify(transactionRepository).findById(uuid);
        verify(transactionDtoMapper).mapEntityToDto(transaction);
    }

    @Test
    void findById_nonExistentTransaction_throwsDataNotFoundException() {
        // given
        when(transactionRepository.findById(uuid)).thenReturn(Optional.empty());
        // when, then
        assertThrows(DataNotFoundException.class, () -> transactionService.findById(strUuid));
        verify(transactionRepository).findById(uuid);
        verifyNoInteractions(transactionDtoMapper);
    }

    @Test
    void findById_invalidUuid_throwsIllegalArgumentException() {
        // given
        String invalidUuid = "invalid_uuid";
        // when, then
        assertThrows(IllegalArgumentException.class, () -> transactionService.findById(invalidUuid));
    }

    @Test
    void findById_nullUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.findById(null));
    }

    @Test
    void findOutgoingTransactions_success() {
        // given
        when(transactionRepository.findTransactionsByDebitAccountUuid(uuid)).thenReturn(transactions);
        when(transactionDtoMapper.mapEntityToDto(transaction1)).thenReturn(transactionDto1);
        when(transactionDtoMapper.mapEntityToDto(transaction2)).thenReturn(transactionDto2);
        // when
        List<TransactionDto> actual = transactionService.findOutgoingTransactions(String.valueOf(uuid));
        // then
        assertEquals(expected, actual);
        verify(transactionRepository).findTransactionsByDebitAccountUuid(uuid);
        verify(transactionDtoMapper, times(2)).mapEntityToDto(any(Transaction.class));
    }

    @Test
    void findOutgoingTransactions_invalidUuid_throwsIllegalArgumentException() {
        // given
        String invalidUuid = "invalid_uuid";
        // when, then
        assertThrows(IllegalArgumentException.class, () -> transactionService.findOutgoingTransactions(invalidUuid));
    }

    @Test
    void findOutgoingTransactions_nullUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.findOutgoingTransactions(null));
    }

    @Test
    void findIncomingTransactions_success() {
        /// given
        when(transactionRepository.findTransactionsByCreditAccountUuid(uuid)).thenReturn(transactions);
        when(transactionDtoMapper.mapEntityToDto(transaction1)).thenReturn(transactionDto1);
        when(transactionDtoMapper.mapEntityToDto(transaction2)).thenReturn(transactionDto2);
        // when
        List<TransactionDto> actual = transactionService.findIncomingTransactions(String.valueOf(uuid));
        // then
        assertEquals(expected, actual);
        verify(transactionRepository).findTransactionsByCreditAccountUuid(uuid);
        verify(transactionDtoMapper, times(2)).mapEntityToDto(any(Transaction.class));
    }

    @Test
    void findIncomingTransactions_invalidUuid_throwsIllegalArgumentException() {
        // given
        String invalidUuid = "invalid_uuid";
        // when, then
        assertThrows(IllegalArgumentException.class, () -> transactionService.findIncomingTransactions(invalidUuid));
    }

    @Test
    void findIncomingTransactions_nullUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.findIncomingTransactions(null));
    }

    @Test
    void findAllTransactionsByClientId_success() {
        // given
        when(transactionRepository.findAllTransactionsWhereClientIdIs(uuid)).thenReturn(transactions);
        when(transactionDtoMapper.mapEntityToDto(transaction1)).thenReturn(transactionDto1);
        when(transactionDtoMapper.mapEntityToDto(transaction2)).thenReturn(transactionDto2);
        // when
        List<TransactionDto> actual = transactionService.findAllTransactionsByClientId(String.valueOf(uuid));
        // then
        assertEquals(expected, actual);
        verify(transactionRepository).findAllTransactionsWhereClientIdIs(uuid);
        verify(transactionDtoMapper, times(2)).mapEntityToDto(any(Transaction.class));
    }

    @Test
    void findAllTransactionsByClientId_invalidUuid_throwsIllegalArgumentException() {
        // given
        String invalidUuid = "invalid_uuid";
        // when, then
        assertThrows(IllegalArgumentException.class, () -> transactionService.findAllTransactionsByClientId(invalidUuid));
    }

    @Test
    void findAllTransactionsByClientId_nullUuid_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> transactionService.findAllTransactionsByClientId(null));
    }

    @Test
    void transferFunds_validData_success() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100);
        transaction.setAmount(amount);

        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        when(accountService.findById(transaction.getDebitAccountUuid())).thenReturn(sender);
        when(accountService.findById(transaction.getCreditAccountUuid())).thenReturn(recipient);
        when(clientService.isClientStatusBlocked(sender.getClientUuid())).thenReturn(false);
        when(clientService.isClientStatusBlocked(recipient.getClientUuid())).thenReturn(false);
        // when
        transactionService.transferFunds(transactionDto);
        // then
        verify(transactionDtoMapper).mapDtoToEntity(transactionDto);
        verify(accountService).findById(transaction.getDebitAccountUuid());
        verify(accountService).findById(transaction.getCreditAccountUuid());
        verify(clientService).isClientStatusBlocked(sender.getClientUuid());
        verify(clientService).isClientStatusBlocked(recipient.getClientUuid());
        verify(accountService).update(sender.getUuid(), sender);
        verify(accountService).update(recipient.getUuid(), recipient);
        verify(transactionRepository).save(transaction);
        assertEquals(recipient.getBalance(), amount);
    }

    @Test
    void transferFunds_withNegativeAmount_throwsIllegalArgumentException() {
        // given
        BigDecimal amount = BigDecimal.valueOf(-100);
        transaction.setAmount(amount);

        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        when(accountService.findById(transaction.getDebitAccountUuid())).thenReturn(sender);
        when(accountService.findById(transaction.getCreditAccountUuid())).thenReturn(recipient);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> transactionService.transferFunds(transactionDto));
        verify(transactionDtoMapper).mapDtoToEntity(transactionDto);
        verify(accountService, times(2)).findById(any(UUID.class));
        verifyNoInteractions(clientService);
        verifyNoMoreInteractions(accountService);
        verify(transactionRepository, never()).save(transaction);
    }

    @Test
    void transferFunds_nullSenderBalance_throwsIllegalArgumentException() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100);
        transaction.setAmount(amount);
        sender.setBalance(null);

        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        when(accountService.findById(transaction.getDebitAccountUuid())).thenReturn(sender);
        when(accountService.findById(transaction.getCreditAccountUuid())).thenReturn(recipient);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> transactionService.transferFunds(transactionDto));
    }

    @Test
    void transferFunds_nullSenderAccountStatus_throwsIllegalArgumentException() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100);
        transaction.setAmount(amount);
        sender.setStatus(null);

        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        when(accountService.findById(transaction.getDebitAccountUuid())).thenReturn(sender);
        when(accountService.findById(transaction.getCreditAccountUuid())).thenReturn(recipient);
        // when, then
        assertThrows(IllegalArgumentException.class, () -> transactionService.transferFunds(transactionDto));
    }

    @Test
    void transferFunds_senderBalanceIsTooLow_throwsInsufficientFundsException() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100);
        transaction.setAmount(amount);
        sender.setBalance(BigDecimal.valueOf(50));

        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        when(accountService.findById(transaction.getDebitAccountUuid())).thenReturn(sender);
        when(accountService.findById(transaction.getCreditAccountUuid())).thenReturn(recipient);
        // when, then
        assertThrows(InsufficientFundsException.class, () -> transactionService.transferFunds(transactionDto));
        verify(transactionDtoMapper).mapDtoToEntity(transactionDto);
        verifyNoInteractions(clientService);
        verifyNoMoreInteractions(accountDtoMapper);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void transferFunds_senderAccountIsNotActive_throwsTransactionNotAllowedException() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100);
        transaction.setAmount(amount);
        sender.setStatus(AccountStatus.CLOSED);

        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        when(accountService.findById(transaction.getDebitAccountUuid())).thenReturn(sender);
        when(accountService.findById(transaction.getCreditAccountUuid())).thenReturn(recipient);
        // when, then
        assertThrows(TransactionNotAllowedException.class, () -> transactionService.transferFunds(transactionDto));
        verify(transactionDtoMapper).mapDtoToEntity(transactionDto);
        verifyNoInteractions(clientService);
        verifyNoMoreInteractions(accountDtoMapper);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void transferFunds_recipientIsNotActive_throwsTransactionNotAllowedException() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100);
        transaction.setAmount(amount);
        recipient.setStatus(AccountStatus.CLOSED);

        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        when(accountService.findById(transaction.getDebitAccountUuid())).thenReturn(sender);
        when(accountService.findById(transaction.getCreditAccountUuid())).thenReturn(recipient);
        // when, then
        assertThrows(TransactionNotAllowedException.class, () -> transactionService.transferFunds(transactionDto));
        verify(transactionDtoMapper).mapDtoToEntity(transactionDto);
        verifyNoInteractions(clientService);
        verifyNoMoreInteractions(accountDtoMapper);
        verifyNoMoreInteractions(accountService);
        verifyNoInteractions(transactionRepository);
    }

    @Test
    void transferFunds_clientNotActive_throwsTransactionNotAllowedException() {
        // given
        BigDecimal amount = BigDecimal.valueOf(100);
        transaction.setAmount(amount);

        when(transactionDtoMapper.mapDtoToEntity(transactionDto)).thenReturn(transaction);
        when(accountService.findById(transaction.getDebitAccountUuid())).thenReturn(sender);
        when(accountService.findById(transaction.getCreditAccountUuid())).thenReturn(recipient);
        when(clientService.isClientStatusBlocked(sender.getClientUuid())).thenReturn(false);
        when(clientService.isClientStatusBlocked(recipient.getClientUuid())).thenReturn(true);
        // when, then
        assertThrows(TransactionNotAllowedException.class, () -> transactionService.transferFunds(transactionDto));
    }
}
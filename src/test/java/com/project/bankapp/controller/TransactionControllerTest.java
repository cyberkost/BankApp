package com.project.bankapp.controller;

import com.project.bankapp.dto.TransactionDto;
import com.project.bankapp.entity.Transaction;
import com.project.bankapp.service.TransactionService;
import com.project.bankapp.utils.session.SessionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {
    @Mock
    TransactionService transactionService;
    @Mock
    SessionUtil sessionUtil;
    @InjectMocks
    TransactionController transactionController;
    String uuid;
    String userName;

    @BeforeEach
    void setUp() {
        uuid = "7bcf30be-8c6e-4e10-a73b-706849fc94dc";
        userName = "test@mail.com";
    }

    @Test
    void createTransaction_success() {
        // given
        TransactionDto transactionDto = TransactionDto.builder().build();
        TransactionDto createdTransactionDto = TransactionDto.builder().build();
        // when
        ResponseEntity<TransactionDto> actual = transactionController.createTransaction(transactionDto);
        // then
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(createdTransactionDto, actual.getBody());
        verify(transactionService).create(transactionDto);
    }

    @Test
    void findAllTransactions_success() {
        // given
        List<TransactionDto> expected = List.of(TransactionDto.builder().build(), TransactionDto.builder().build());
        when(transactionService.findAll()).thenReturn(expected);
        // when
        ResponseEntity<List<TransactionDto>> actual = transactionController.findAllTransactions();
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(transactionService).findAll();
    }

    @Test
    void findAllTransactions_withEmptyList_returnsNoContentStatus() {
        // given
        List<TransactionDto> expected = Collections.emptyList();
        when(transactionService.findAll()).thenReturn(expected);
        // when
        ResponseEntity<List<TransactionDto>> actual = transactionController.findAllTransactions();
        // then
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertNull(actual.getBody());
        verify(transactionService).findAll();
    }

    @Test
    void findTransactionByUuid_success() {
        // given
        TransactionDto expected = TransactionDto.builder().build();
        when(transactionService.findById(uuid)).thenReturn(expected);
        // when
        ResponseEntity<TransactionDto> actual = transactionController.findTransactionByUuid(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(transactionService).findById(uuid);
    }

    @Test
    void findOutgoingTransactions_success() {
        // given
        List<TransactionDto> expected = List.of(TransactionDto.builder().build(), TransactionDto.builder().build());
        when(transactionService.findOutgoingTransactions(uuid)).thenReturn(expected);
        // when
        ResponseEntity<List<TransactionDto>> actual = transactionController.findOutgoingTransactions(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(transactionService).findOutgoingTransactions(uuid);
    }

    @Test
    void findOutgoingTransactions_withEmptyList_returnsNoContentStatus() {
        // given
        List<TransactionDto> expected = Collections.emptyList();
        when(transactionService.findOutgoingTransactions(uuid)).thenReturn(expected);
        // when
        ResponseEntity<List<TransactionDto>> actual = transactionController.findOutgoingTransactions(uuid);
        // then
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertNull(actual.getBody());
        verify(transactionService).findOutgoingTransactions(uuid);
    }

    @Test
    void findIncomingTransactions_success() {
        // given
        List<TransactionDto> expected = List.of(TransactionDto.builder().build(), TransactionDto.builder().build());
        when(transactionService.findIncomingTransactions(uuid)).thenReturn(expected);
        // when
        ResponseEntity<List<TransactionDto>> actual = transactionController.findIncomingTransactions(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(transactionService).findIncomingTransactions(uuid);
    }

    @Test
    void findIncomingTransactions_withEmptyList_returnsNoContentStatus() {
        // given
        List<TransactionDto> expected = Collections.emptyList();
        when(transactionService.findIncomingTransactions(uuid)).thenReturn(expected);
        // when
        ResponseEntity<List<TransactionDto>> actual = transactionController.findIncomingTransactions(uuid);
        // then
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertNull(actual.getBody());
        verify(transactionService).findIncomingTransactions(uuid);
    }

    @Test
    void transferFunds_success() {
        // given
        TransactionDto transactionDto = TransactionDto.builder().build();
        // when
        ResponseEntity<String> actual = transactionController.transferFunds(transactionDto);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(transactionService).transferFunds(transactionDto);
    }

    @Test
    void findAllTransactionsByClient_success() {
        // given
        List<TransactionDto> expected = List.of(TransactionDto.builder().build(), TransactionDto.builder().build());
        when(sessionUtil.getCurrentUsername()).thenReturn(userName);
        when(transactionService.findAllByUsername(userName)).thenReturn(expected);
        // when
        ResponseEntity<List<TransactionDto>> actual = transactionController.findAllTransactionsByClient();
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(transactionService).findAllByUsername(userName);
    }

    @Test
    void findAllTransactionsByClient_withEmptyList_returnsNoContentStatus() {
        // given
        List<TransactionDto> expected = Collections.emptyList();
        when(sessionUtil.getCurrentUsername()).thenReturn(userName);
        when(transactionService.findAllByUsername(userName)).thenReturn(expected);
        // when
        ResponseEntity<List<TransactionDto>> actual = transactionController.findAllTransactionsByClient();
        // then
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertNull(actual.getBody());
        verify(transactionService).findAllByUsername(userName);
    }
}
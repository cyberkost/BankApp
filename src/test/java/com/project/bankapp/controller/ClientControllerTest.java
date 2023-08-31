package com.project.bankapp.controller;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.service.ClientService;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    @Mock
    ClientService clientService;
    @InjectMocks
    ClientController clientController;
    String uuid;

    @BeforeEach
    void setUp() {
        uuid = "7bcf30be-8c6e-4e10-a73b-706849fc94dc";
    }

    @Test
    void createClient_success() {
        // when
        ClientDto clientDto = ClientDto.builder().build();
        ClientDto createdClientDto = ClientDto.builder().build();
        // when
        ResponseEntity<ClientDto> actual = clientController.createClient(clientDto);
        // then
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(createdClientDto, actual.getBody());
        verify(clientService).create(clientDto);
    }

    @Test
    void createClient_emptyClientDto_savesNoData() {
        // when
        ResponseEntity<ClientDto> actual = clientController.createClient(null);
        // then
        assertNull(actual.getBody());
        verify(clientService, never()).create(any(ClientDto.class));
    }

    @Test
    void findAllClients_success() {
        // given
        List<ClientDto> expected = List.of(ClientDto.builder().build(), ClientDto.builder().build());
        when(clientService.findAllNotDeleted()).thenReturn(expected);
        // when
        ResponseEntity<List<ClientDto>> actual = clientController.findAllClients();
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(clientService).findAllNotDeleted();
    }

    @Test
    void findAllClients_withEmptyList_returnsNoContentStatus() {
        // given
        List<ClientDto> expected = Collections.emptyList();
        when(clientService.findAllNotDeleted()).thenReturn(expected);
        // when
        ResponseEntity<List<ClientDto>> actual = clientController.findAllClients();
        // then
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertNull(actual.getBody());
        verify(clientService).findAllNotDeleted();
    }

    @Test
    void getClientByUuid_success() {
        // given
        ClientDto expected = ClientDto.builder().build();
        when(clientService.findById(uuid)).thenReturn(expected);
        // when
        ResponseEntity<ClientDto> actual = clientController.getClientByUuid(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(clientService).findById(uuid);
    }

    @Test
    void updateClient_success() {
        // given
        ClientDto expected = ClientDto.builder().build();
        // when
        ResponseEntity<ClientDto> actual = clientController.updateClient(uuid, expected);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
        verify(clientService).update(uuid, expected);
    }

    @Test
    void deleteClient_success() {
        // when
        ResponseEntity<String> actual = clientController.deleteClient(uuid);
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        verify(clientService).delete(uuid);
    }

    @Test
    void findActiveClients_returnsNoContent() {
        // given
        List<ClientDto> expected = Collections.emptyList();
        when(clientService.findActiveClients()).thenReturn(expected);
        // when
        ResponseEntity<List<ClientDto>> actual = clientController.findActiveClients();
        // then
        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertNull(actual.getBody());
    }

    @Test
    void findActiveClients_returnsOkWithClientList() {
        // given
        List<ClientDto> expected = List.of(ClientDto.builder().build(), ClientDto.builder().build()
        );
        when(clientService.findActiveClients()).thenReturn(expected);
        // when
        ResponseEntity<List<ClientDto>> actual = clientController.findActiveClients();
        // then
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expected, actual.getBody());
    }
}
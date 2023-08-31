package com.project.bankapp.service.impl;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.dto.mapper.client.ClientCreationMapper;
import com.project.bankapp.dto.mapper.client.ClientDtoMapper;
import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.Manager;
import com.project.bankapp.entity.enums.ClientStatus;
import com.project.bankapp.entity.enums.ManagerStatus;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.repository.ClientRepository;
import com.project.bankapp.service.ManagerService;
import com.project.bankapp.utils.updater.ClientUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientServiceImplTest {
    @Mock
    ClientRepository clientRepository;
    @Mock
    ClientCreationMapper clientCreationMapper;
    @Mock
    ClientDtoMapper clientDtoMapper;
    @Mock
    ClientUpdater clientUpdater;
    @Mock
    ManagerService managerService;
    @InjectMocks
    ClientServiceImpl clientService;
    Client client1;
    Client client2;
    ClientDto clientDto1;
    ClientDto clientDto2;
    UUID uuid;
    List<Client> clients;
    List<ClientDto> clientDtoList;

    @BeforeEach
    void setUp() {
        client1 = Client.builder().build();
        client2 = Client.builder().build();
        clientDto1 = ClientDto.builder().build();
        clientDto2 = ClientDto.builder().build();
        uuid = UUID.fromString("d358838e-1134-4101-85ac-5d99e8debfae");
        clients = List.of(client1, client2);
        clientDtoList = List.of(clientDto1, clientDto2);
    }

    @Test
    void create_success_withNullManagerUuid() {
        // given
        List<Manager> managers = List.of(new Manager(), new Manager());
        ManagerStatus status = ManagerStatus.ACTIVE;
        Manager firstManager = new Manager();
        when(clientCreationMapper.mapDtoToEntity(clientDto1)).thenReturn(client1);
        when(managerService.findManagersSortedByClientQuantityWhereManagerStatusIs(status))
                .thenReturn(managers);
        when(managerService.getFirstManager(managers)).thenReturn(firstManager);
        // when
        clientService.create(clientDto1);
        // then
        assertEquals(firstManager.getUuid(), client1.getManagerUuid());
        verify(clientCreationMapper).mapDtoToEntity(clientDto1);
        verify(managerService).findManagersSortedByClientQuantityWhereManagerStatusIs(status);
        verify(managerService).getFirstManager(managers);
        verify(clientRepository).save(client1);
    }

    @Test
    void create_success_withExistingManagerUuid() {
        // given
        UUID managerUuid = UUID.randomUUID();
        client1.setManagerUuid(managerUuid);
        clientDto1.setManagerUuid(String.valueOf(managerUuid));
        when(clientCreationMapper.mapDtoToEntity(clientDto1)).thenReturn(client1);
        // when
        clientService.create(clientDto1);
        // then
        assertEquals(managerUuid, client1.getManagerUuid());
        verify(clientCreationMapper).mapDtoToEntity(clientDto1);
        verify(clientRepository).save(client1);
    }

    @Test
    void findAll_success() {
        // given
        List<ClientDto> expected = List.of(clientDto1, clientDto2);
        when(clientRepository.findAll()).thenReturn(clients);
        when(clientDtoMapper.mapEntityToDto(client1)).thenReturn(clientDto1);
        when(clientDtoMapper.mapEntityToDto(client2)).thenReturn(clientDto2);
        // when
        List<ClientDto> actual = clientService.findAll();
        // then
        assertEquals(expected, actual);
        verify(clientRepository).findAll();
        verify(clientDtoMapper, times(2)).mapEntityToDto(any(Client.class));
    }

    @Test
    void findAllNotDeleted_success() {
        // given
        List<ClientDto> expected = List.of(clientDto1, clientDto2);
        when(clientRepository.findAllNotDeleted()).thenReturn(clients);
        when(clientDtoMapper.mapEntityToDto(client1)).thenReturn(clientDto1);
        when(clientDtoMapper.mapEntityToDto(client2)).thenReturn(clientDto2);
        // when
        List<ClientDto> actual = clientService.findAllNotDeleted();
        // then
        assertEquals(expected, actual);
        verify(clientRepository).findAllNotDeleted();
        verify(clientDtoMapper, times(2)).mapEntityToDto(any(Client.class));
    }

    @Test
    void findDeletedClients_success() {
        // given
        List<ClientDto> expected = List.of(clientDto1, clientDto2);
        when(clientRepository.findAllDeleted()).thenReturn(clients);
        when(clientDtoMapper.mapEntityToDto(client1)).thenReturn(clientDto1);
        when(clientDtoMapper.mapEntityToDto(client2)).thenReturn(clientDto2);
        // when
        List<ClientDto> actual = clientService.findDeletedClients();
        // then
        assertEquals(expected, actual);
        verify(clientRepository).findAllDeleted();
        verify(clientDtoMapper, times(2)).mapEntityToDto(any(Client.class));
    }

    @Test
    void findById_success() {
        // given
        ClientDto expected = clientDto1;
        when(clientRepository.findById(uuid)).thenReturn(Optional.ofNullable(client1));
        when(clientDtoMapper.mapEntityToDto(client1)).thenReturn(clientDto1);
        // when
        ClientDto actual = clientService.findById(String.valueOf(uuid));
        // then
        assertEquals(expected, actual);
        verify(clientRepository).findById(uuid);
        verify(clientDtoMapper).mapEntityToDto(client1);
    }

    @Test
    void findById_clientNotFound_throwsDataNotFoundException() {
        // given
        String strUuid = "d358838e-1134-4101-85ac-5d99e8debfae";
        when(clientRepository.findById(uuid)).thenReturn(Optional.empty());
        // when, then
        assertThrows(DataNotFoundException.class, () -> clientService.findById(strUuid));
        verify(clientRepository).findById(uuid);
    }

    @Test
    void findActiveClients_success() {
        // given
        ClientStatus status = ClientStatus.ACTIVE;
        List<ClientDto> expected = List.of(clientDto1, clientDto2);
        when(clientRepository.findClientsByStatusIs(status)).thenReturn(clients);
        when(clientDtoMapper.mapEntityToDto(client1)).thenReturn(clientDto1);
        when(clientDtoMapper.mapEntityToDto(client2)).thenReturn(clientDto2);
        // when
        List<ClientDto> actual = clientService.findActiveClients();
        // then
        assertEquals(expected, actual);
        verify(clientRepository).findClientsByStatusIs(status);
        verify(clientDtoMapper, times(2)).mapEntityToDto(any(Client.class));
    }

    @Test
    void update() {
        // given
        ClientDto updatedClientDto = clientDto1;
        Client updatedClient = client1;
        Client clientToUpdate = client2;

        when(clientCreationMapper.mapDtoToEntity(updatedClientDto)).thenReturn(updatedClient);
        when(clientRepository.findById(uuid)).thenReturn(Optional.ofNullable(clientToUpdate));
        when(clientUpdater.update(clientToUpdate, updatedClient)).thenReturn(client1);
        // when
        clientService.update(String.valueOf(uuid), updatedClientDto);
        // then
        verify(clientCreationMapper).mapDtoToEntity(updatedClientDto);
        verify(clientRepository).findById(uuid);
        verify(clientUpdater).update(clientToUpdate, updatedClient);
        verify(clientRepository).save(client1);
    }

    @Test
    void update_clientNotFound_throwsDataNotFoundException() {
        // given
        String strUuid = "d358838e-1134-4101-85ac-5d99e8debfae";
        ClientDto updatedClientDto = clientDto1;
        when(clientRepository.findById(uuid)).thenReturn(Optional.empty());
        // when, then
        assertThrows(DataNotFoundException.class, () -> clientService.update(strUuid, updatedClientDto));
        verify(clientRepository).findById(uuid);
    }

    @Test
    void delete_success() {
        // given
        when(clientRepository.findById(uuid)).thenReturn(Optional.ofNullable(client1));
        // when
        clientService.delete(String.valueOf(uuid));
        // then
        assertTrue(client1.isDeleted());
        verify(clientRepository).findById(uuid);
        verify(clientRepository).save(client1);
    }

    @Test
    void delete_clientNotFound_throwsDataNotFoundException() {
        // given
        String strUuid = "d358838e-1134-4101-85ac-5d99e8debfae";
        when(clientRepository.findById(uuid)).thenReturn(Optional.empty());
        // when, then
        assertThrows(DataNotFoundException.class, () -> clientService.delete(strUuid));
        verify(clientRepository).findById(uuid);
    }

    @Test
    void isClientStatusActive_clientIsActive_success() {
        // given
        Boolean expected = true;
        when(clientRepository.isClientStatusBlocked(uuid)).thenReturn(true);
        // when
        Boolean actual = clientService.isClientStatusActive(uuid);
        // then
        assertEquals(expected, actual);
        verify(clientRepository).isClientStatusBlocked(uuid);
    }

    @Test
    void isClientStatusActive_clientStatusBlocked_success() {
        // given
        Boolean expected = false;
        when(clientRepository.isClientStatusBlocked(uuid)).thenReturn(false);
        // when
        Boolean actual = clientService.isClientStatusActive(uuid);
        // then
        assertEquals(expected, actual);
        verify(clientRepository).isClientStatusBlocked(uuid);
    }
}
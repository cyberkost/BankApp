package com.project.bankapp.service.impl;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.dto.mapper.client.ClientCreationMapper;
import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.enums.ClientStatus;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.dto.mapper.client.ClientDtoMapper;
import com.project.bankapp.repository.ClientRepository;
import com.project.bankapp.service.ClientService;
import com.project.bankapp.utils.updater.ClientUpdater;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;
    private final ClientCreationMapper clientCreationMapper;
    private final ClientUpdater clientUpdater;

    @Override
    @Transactional
    public void create(ClientDto clientDto) {
        Client client = clientDtoMapper.mapDtoToEntity(clientDto);
        client.setStatus(ClientStatus.ACTIVE);
        clientRepository.save(client);
    }

    @Override
    public void save(Client client) {
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public List<ClientDto> findAll() {
        List<Client> clients = clientRepository.findAll();
        clients = clients
                .stream()
                .filter(client -> !client.isDeleted())
                .toList();
        return getDtoList(clients);
    }

    @Override
    @Transactional
    public ClientDto findById(String clientUuid) {
        if (clientUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(clientUuid);
        return clientDtoMapper.mapEntityToDto(
                clientRepository.findById(uuid)
                        .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid))));
    }

    @Override
    @Transactional
    public void update(String clientUuid, ClientDto updatedClientDto) {
        if (clientUuid == null || updatedClientDto == null) {
        throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(clientUuid);
        Client updatedClient = clientCreationMapper.mapDtoToEntity(updatedClientDto);
        Client client = clientRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        client = clientUpdater.update(client, updatedClient);
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void delete(String clientUuid) {
        if (clientUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(clientUuid);
        Client client = clientRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        client.setDeleted(true);
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public List<ClientDto> findDeletedClients() {
        List<Client> deletedClients = clientRepository.findAllDeleted();
        return getDtoList(deletedClients);
    }

    @Override
    @Transactional
    public List<ClientDto> findAllNotDeleted() {
        List<Client> clients = clientRepository.findAllNotDeleted();
        return getDtoList(clients);
    }

    @Override
    @Transactional
    public List<ClientDto> findActiveClients() {
        List<Client> clients = clientRepository.findClientsByStatusIs(ClientStatus.ACTIVE);
        return getDtoList(clients);
    }

    public List<ClientDto> getDtoList(List<Client> clientList) {
        return Optional.ofNullable(clientList)
                .orElseThrow(() -> new DataNotFoundException("list is null"))
                .stream()
                .filter(Objects::nonNull)
                .map(clientDtoMapper::mapEntityToDto)
                .toList();
    }
}

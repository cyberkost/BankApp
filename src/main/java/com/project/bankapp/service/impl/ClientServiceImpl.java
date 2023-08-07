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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
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
        log.info("client created");
    }

    @Override
    @Transactional
    public void save(Client client) {
        log.info("saving client into db");
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public List<ClientDto> findAll() {
        log.info("retrieving list of clients");
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
        log.info("retrieving client by id {}", clientUuid);
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
        log.info("updated client id {}", uuid);
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
        log.info("deleted client id {}", uuid);
    }

    @Override
    @Transactional
    public List<ClientDto> findDeletedClients() {
        log.info("retrieving list of deleted clients");
        List<Client> deletedClients = clientRepository.findAllDeleted();
        return getDtoList(deletedClients);
    }

    @Override
    @Transactional
    public List<ClientDto> findAllNotDeleted() {
        log.info("retrieving list of not deleted clients");
        List<Client> clients = clientRepository.findAllNotDeleted();
        return getDtoList(clients);
    }

    @Override
    @Transactional
    public List<ClientDto> findActiveClients() {
        log.info("retrieving list of active clients");
        List<Client> clients = clientRepository.findClientsByStatusIs(ClientStatus.ACTIVE);
        return getDtoList(clients);
    }

    private List<ClientDto> getDtoList(List<Client> clientList) {
        return Optional.ofNullable(clientList)
                .orElseThrow(() -> new DataNotFoundException("list is null"))
                .stream()
                .filter(Objects::nonNull)
                .map(clientDtoMapper::mapEntityToDto)
                .toList();
    }
}

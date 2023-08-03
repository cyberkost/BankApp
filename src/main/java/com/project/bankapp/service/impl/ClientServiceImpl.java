package com.project.bankapp.service.impl;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.enums.ClientStatus;
import com.project.bankapp.exception.DataNotFoundException;
import com.project.bankapp.dto.mapper.client.ClientDtoMapper;
import com.project.bankapp.repository.ClientRepository;
import com.project.bankapp.service.ClientService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientDtoMapper clientDtoMapper;

    @Override
    @Transactional
    public void create(ClientDto clientDto) {
        Client client = clientDtoMapper.mapDtoToEntity(clientDto);
        client.setStatus(ClientStatus.ACTIVE);
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
        return clientDtoMapper.getDtoList(clients);
    }

    @Override
    @Transactional
    public ClientDto findById(String clientUuid) {
        if (clientUuid == null) {
            throw new IllegalArgumentException();
        }
        UUID uuid = UUID.fromString(clientUuid);
        Client client = clientRepository.findById(uuid)
                .orElseThrow(() -> new DataNotFoundException(String.valueOf(uuid)));
        return clientDtoMapper.mapEntityToDto(client);
    }

    @Override
    @Transactional
    public void update(String uuid, ClientDto updatedClientDto) {
        Client existingClient = clientRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new DataNotFoundException("Client not found"));
        existingClient.setManagerUuid(updatedClientDto.getManagerUuid() != null ? UUID.fromString(updatedClientDto.getManagerUuid()) : null);
        existingClient.setStatus(updatedClientDto.getStatus() != null ? ClientStatus.valueOf(updatedClientDto.getStatus()) : null);
        existingClient.setFirstName(updatedClientDto.getFirstName());
        existingClient.setLastName(updatedClientDto.getLastName());
        existingClient.setEmail(updatedClientDto.getEmail());
        existingClient.setAddress(updatedClientDto.getAddress());
        existingClient.setPhone(updatedClientDto.getPhone());
        clientRepository.save(existingClient);
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
        return clientDtoMapper.getDtoList(deletedClients);
    }

    @Override
    @Transactional
    public List<ClientDto> findAllNotDeleted() {
        List<Client> clients = clientRepository.findAllNotDeleted();
        return clientDtoMapper.getDtoList(clients);
    }

    @Override
    @Transactional
    public List<ClientDto> findActiveClients() {
        List<Client> clients = clientRepository.findClientsByStatusIs(ClientStatus.ACTIVE);
        return clientDtoMapper.getDtoList(clients);
    }
}

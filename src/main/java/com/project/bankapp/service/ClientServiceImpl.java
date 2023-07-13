package com.project.bankapp.service;

import com.project.bankapp.entity.Client;
import com.project.bankapp.repository.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
@RequiredArgsConstructor
@Component
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    @Override
    @Transactional
    public void createClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    @Transactional
    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Override
    @Transactional
    public Client findByUuid(UUID uuid) {
        Optional<Client> clientOptional = clientRepository.findById(uuid);
        return clientOptional.orElseThrow(() -> new RuntimeException());
    }

    @Override
    @Transactional
    public void updateClient(Client updatedData, UUID uuid) {
        Optional<Client> clientOptional = clientRepository.findById(uuid);
        Client clientForUpdate = clientOptional.orElseThrow(() -> new RuntimeException());
        if (updatedData.getFirstName() != null) {
            clientForUpdate.setFirstName(updatedData.getFirstName());
        }
    }

    @Override
    @Transactional
    public void deleteClient(UUID uuid) {
        clientRepository.deleteById(uuid);
    }
}

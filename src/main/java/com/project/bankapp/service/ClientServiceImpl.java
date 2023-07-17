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
        if (updatedData.getLastName() != null) {
            clientForUpdate.setLastName(updatedData.getLastName());
        }
        if (updatedData.getAge() != null) {
            clientForUpdate.setAge(updatedData.getAge());
        }
        if (updatedData.getCitizenship() != null) {
            clientForUpdate.setCitizenship(updatedData.getCitizenship());
        }
        if (updatedData.getEmail() != null) {
            clientForUpdate.setEmail(updatedData.getEmail());
        }
        if (updatedData.getAddress() != null) {
            clientForUpdate.setAddress(updatedData.getAddress());
        }
        if (updatedData.getPhone() != null) {
            clientForUpdate.setPhone(updatedData.getPhone());
        }
        if (updatedData.getStatus() != null) {
            clientForUpdate.setStatus(updatedData.getStatus());
        }
    }

    @Override
    @Transactional
    public void deleteClient(UUID uuid) {
        clientRepository.deleteById(uuid);
    }

    @Override
    public List<Client> findByFirstName(String firstName) {
        return clientRepository.findClientByFirstName(firstName);
    }


}

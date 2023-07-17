package com.project.bankapp.service;

import com.project.bankapp.entity.Client;

import java.util.List;
import java.util.UUID;

public interface ClientService {
    void createClient(Client client);
    List<Client> findAllClients();
    Client findByUuid(UUID uuid);
    void updateClient(Client client, UUID uuid);
    void deleteClient(UUID uuid);
    List<Client> findByFirstName(String firstName);
}

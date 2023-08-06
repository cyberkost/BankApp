package com.project.bankapp.service;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.entity.Client;

import java.util.List;
import java.util.UUID;


public interface ClientService {
    void create(ClientDto clientDto);

    void save(Client client);

    List<ClientDto> findAll();

    ClientDto findById(String uuid);

    void update(String uuid, ClientDto updatedClientDto);

    void delete(String uuid);

    List<ClientDto> findDeletedClients();

    List<ClientDto> findAllNotDeleted();

    List<ClientDto> findActiveClients();

}

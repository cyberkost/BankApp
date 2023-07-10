package com.project.bankapp.service;

import com.project.bankapp.entity.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ClientServiceImpl implements ClientService {

    private static final Map<Integer, Client> CLIENT_REPOSITORY_MAP = new HashMap<>();

    private static final AtomicInteger CLIENT_ID_HOLDER = new AtomicInteger();

    @Bean
    ClientServiceImpl clientService() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        return clientService;
    }

    @Override
    public void create(Client client) {
        final int id = CLIENT_ID_HOLDER.incrementAndGet();
        client.setUuid(UUID.randomUUID());
        CLIENT_REPOSITORY_MAP.put(id, client);
    }

    @Override
    public List<Client> readAll() {
        return new ArrayList<>(CLIENT_REPOSITORY_MAP.values());
    }

    @Override
    public Client read(int id) {
        return CLIENT_REPOSITORY_MAP.get(id);
    }

    @Override
    public boolean update(Client client, int id) {
        if (CLIENT_REPOSITORY_MAP.containsKey(id)) {
            client.setUuid(UUID.randomUUID());
            CLIENT_REPOSITORY_MAP.put(id, client);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return CLIENT_REPOSITORY_MAP.remove(id) != null;
    }
}

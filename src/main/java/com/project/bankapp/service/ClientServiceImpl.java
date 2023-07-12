package com.project.bankapp.service;

import com.project.bankapp.entity.Client;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class ClientServiceImpl implements ClientService {

    @Override
    public void create(Client client) {

    }

    @Override
    public List<Client> readAll() {
        return null;
    }

    @Override
    public Client read(int id) {
        return null;
    }

    @Override
    public boolean update(Client client, int id) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}

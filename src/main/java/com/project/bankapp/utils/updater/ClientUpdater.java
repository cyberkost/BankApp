package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientUpdater {

    public Client update(Client client, Client clientUpdate) {
        if (client == null || clientUpdate == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return updateProperties(client, clientUpdate);
    }

    public Client updateProperties(Client client, Client clientUpdate) {
        if (clientUpdate.getManagerUuid() != null) {
            client.setManagerUuid(clientUpdate.getManagerUuid());
        }
        if (clientUpdate.getStatus() != null) {
            client.setStatus(clientUpdate.getStatus());
        }
        if (clientUpdate.getFirstName() != null) {
            client.setFirstName(clientUpdate.getFirstName());
        }
        if (clientUpdate.getLastName() != null) {
            client.setLastName(clientUpdate.getLastName());
        }
        if (clientUpdate.getEmail() != null) {
            client.setEmail(clientUpdate.getEmail());
        }
        if (clientUpdate.getAddress() != null) {
            client.setAddress(clientUpdate.getAddress());
        }
        if (clientUpdate.getPhone() != null) {
            client.setPhone(clientUpdate.getPhone());
        }
        return client;
    }
}

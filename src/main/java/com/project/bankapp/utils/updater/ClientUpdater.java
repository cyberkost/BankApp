package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Client;
import org.springframework.stereotype.Component;

/**
 * A utility class for updating client properties based on a client update DTO.
 */
@Component
public class ClientUpdater {
    /**
     * Updates the provided client's properties based on the information in the client update DTO.
     *
     * @param client       The original client instance to be updated.
     * @param clientUpdate The client update DTO containing the updated information.
     * @return The updated client instance.
     * @throws IllegalArgumentException if either the client or clientUpdate argument is null.
     */
    public Client update(Client client, Client clientUpdate) {
        if (client == null || clientUpdate == null) {
            throw new IllegalArgumentException("argument is null");
        }
        return updateProperties(client, clientUpdate);
    }

    /**
     * Updates specific properties of the client based on the information in the client update DTO.
     *
     * @param client       The original client instance to be updated.
     * @param clientUpdate The client update DTO containing the updated information.
     * @return The updated client instance with the specified properties modified.
     */
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

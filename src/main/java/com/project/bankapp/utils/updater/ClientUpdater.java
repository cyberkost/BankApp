package com.project.bankapp.utils.updater;

import com.project.bankapp.entity.Client;

public interface ClientUpdater {
    /**
     * Updates the provided client's properties based on the information in the client update DTO.
     *
     * @param client       The original client instance to be updated.
     * @param clientUpdate The client update DTO containing the updated information.
     * @return The updated client instance.
     * @throws IllegalArgumentException if either the client or clientUpdate argument is null.
     */
    Client update(Client client, Client clientUpdate);

    /**
     * Updates specific properties of the client based on the information in the client update DTO.
     *
     * @param client       The original client instance to be updated.
     * @param clientUpdate The client update DTO containing the updated information.
     * @return The updated client instance with the specified properties modified.
     */
    Client updateProperties(Client client, Client clientUpdate);
}

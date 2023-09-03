package com.project.bankapp.service;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.entity.Client;

import java.util.List;
import java.util.UUID;

/**
 * Service interface for managing client-related operations.
 */
public interface ClientService {
    /**
     * Creates a new client using the provided client data.
     *
     * @param clientDto The DTO containing client information.
     */
    void create(ClientDto clientDto);

    /**
     * Saves the provided client instance.
     *
     * @param client The client instance to be saved.
     */
    void save(Client client);

    /**
     * Retrieves a list of all clients.
     *
     * @return A list of all client DTOs.
     */
    List<ClientDto> findAll();

    /**
     * Retrieves the client DTO associated with the provided UUID.
     *
     * @param uuid The UUID of the client to retrieve.
     * @return The client DTO.
     */
    ClientDto findById(String uuid);

    /**
     * Updates the client with the provided UUID using the information from the updated client DTO.
     *
     * @param uuid             The UUID of the client to update.
     * @param updatedClientDto The DTO containing updated client information.
     */
    void update(String uuid, ClientDto updatedClientDto);

    /**
     * Deletes the client with the provided UUID.
     *
     * @param uuid The UUID of the client to delete.
     */
    void delete(String uuid);

    /**
     * Retrieves a list of deleted client DTOs.
     *
     * @return A list of deleted client DTOs.
     */
    List<ClientDto> findDeletedClients();

    /**
     * Retrieves a list of all non-deleted client DTOs.
     *
     * @return A list of non-deleted client DTOs.
     */
    List<ClientDto> findAllNotDeleted();

    /**
     * Retrieves a list of active client DTOs.
     *
     * @return A list of active client DTOs.
     */
    List<ClientDto> findActiveClients();

    /**
     * Checks if the client with the provided UUID has an active status.
     *
     * @param uuid The UUID of the client to check.
     * @return true if the client's status is active, false otherwise.
     */
    boolean isClientStatusActive(UUID uuid);

    /**
     * Checks if the Client with the specified UUID has an active status.
     *
     * @param uuid The UUID of the client to check.
     * @return True if the Client has an active status, false otherwise.
     */
    boolean isClientStatusBlocked(UUID uuid);

    Client findClientByEmail(String email);
}

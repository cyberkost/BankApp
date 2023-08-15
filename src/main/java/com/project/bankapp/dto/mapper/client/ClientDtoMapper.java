package com.project.bankapp.dto.mapper.client;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.enums.ClientStatus;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Mapper component for converting between Client entities and ClientDto data transfer objects.
 */
@Component
public class ClientDtoMapper {
    /**
     * Maps a Client entity to a ClientDto.
     *
     * @param client The Client entity to be mapped.
     * @return A ClientDto with mapped data from the given entity.
     * @throws IllegalArgumentException if the input client is null.
     */
    public ClientDto mapEntityToDto(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("client cannot be null");
        }
        return ClientDto.builder()
                .managerUuid(client.getManagerUuid() != null ? client.getManagerUuid().toString() : null)
                .status(client.getStatus() != null ? client.getStatus().name() : null)
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .email(client.getEmail())
                .address(client.getAddress())
                .phone(client.getPhone())
                .build();
    }

    /**
     * Maps a ClientDto to a Client entity.
     *
     * @param clientDto The ClientDto to be mapped.
     * @return A Client entity with mapped data from the given DTO.
     * @throws IllegalArgumentException if the input clientDto is null.
     */
    public Client mapDtoToEntity(ClientDto clientDto) {
        if (clientDto == null) {
            throw new IllegalArgumentException("clientDto cannot be null");
        }
        return Client.builder()
                .managerUuid(clientDto.getManagerUuid() != null ? UUID.fromString(clientDto.getManagerUuid()) : null)
                .status(clientDto.getStatus() != null ? ClientStatus.valueOf(clientDto.getStatus()) : null)
                .firstName(clientDto.getFirstName())
                .lastName(clientDto.getLastName())
                .email(clientDto.getEmail())
                .address(clientDto.getAddress())
                .phone(clientDto.getPhone())
                .build();
    }
}

package com.project.bankapp.dto.mapper.client;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.entity.Client;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between Client entities and ClientDto data transfer objects.
 */
@Component
public class ClientCreationMapper {
    /**
     * Maps a Client entity to a ClientDto.
     *
     * @param entity The Client entity to be mapped.
     * @return A ClientDto containing the mapped information.
     * @throws IllegalArgumentException If the provided entity is null.
     */
    public ClientDto mapEntityToDto(Client entity) {
        if (entity == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return ClientDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .address(entity.getAddress())
                .phone(entity.getPhone())
                .build();
    }

    /**
     * Maps a ClientDto to a Client entity.
     *
     * @param entityDto The ClientDto to be mapped.
     * @return A Client entity containing the mapped information.
     * @throws IllegalArgumentException If the provided entityDto is null.
     */
    public Client mapDtoToEntity(ClientDto entityDto) {
        if (entityDto == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        return Client.builder()
                .firstName(entityDto.getFirstName())
                .lastName(entityDto.getLastName())
                .email(entityDto.getEmail())
                .address(entityDto.getAddress())
                .phone(entityDto.getPhone())
                .build();
    }
}

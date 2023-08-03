package com.project.bankapp.dto.mapper.client;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.entity.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientCreationMapper {
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

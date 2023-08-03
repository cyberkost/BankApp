package com.project.bankapp.dto.mapper.client;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.enums.ClientStatus;
import com.project.bankapp.exception.DataNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
public class ClientDtoMapper {
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
    public List<ClientDto> getDtoList(List<Client> clientList) {
        return Optional.ofNullable(clientList)
                .orElseThrow(() -> new DataNotFoundException("list is null"))
                .stream()
                .filter(Objects::nonNull)
                .map(this::mapEntityToDto)
                .toList();
    }
}

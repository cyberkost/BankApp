package com.project.bankapp.controller;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ClientController {

    private final ClientService clientService;

    @PostMapping(value ="/client/create")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        clientService.create(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDto);
    }

    @GetMapping(value ="/client/find-all")
    public ResponseEntity<List<ClientDto>> findAllClients() {
        List<ClientDto> clientDtoList = clientService.findAllNotDeleted();
        return clientDtoList.isEmpty() ? ResponseEntity.noContent()
                .build() : ResponseEntity.ok(clientDtoList);
    }

    @GetMapping(value ="/client/find/{uuid}")
    public ResponseEntity<ClientDto> getClientByUuid(@PathVariable String uuid) {
        ClientDto clientDto = clientService.findById(uuid);
        return ResponseEntity.ok(clientDto);
    }

    @PutMapping(value ="/client/update/{uuid}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable String uuid, @RequestBody ClientDto updatedClientDto) {
        clientService.update(uuid, updatedClientDto);
        return ResponseEntity.ok(updatedClientDto);
    }

    @DeleteMapping(value ="/client/delete/{uuid}")
    public ResponseEntity<String> deleteClient(@PathVariable String uuid) {
        clientService.delete(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value ="/client/find-all/active-clients")
    public ResponseEntity<List<ClientDto>> findActiveClients() {
        List<ClientDto> clientDtoList = clientService.findActiveClients();
        return clientDtoList.isEmpty() ? ResponseEntity.noContent()
                .build() : ResponseEntity.ok(clientDtoList);
    }
}

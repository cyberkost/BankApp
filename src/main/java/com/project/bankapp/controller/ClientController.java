package com.project.bankapp.controller;

import com.project.bankapp.dto.ClientDto;
import com.project.bankapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @PostMapping(value ="/client/create")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        log.info("endpoint request: create new client");
        clientService.create(clientDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientDto);
    }

    @GetMapping(value ="/client/find-all")
    public ResponseEntity<List<ClientDto>> findAllClients() {
        log.info("endpoint request: find all clients");
        List<ClientDto> clientDtoList = clientService.findAllNotDeleted();
        return clientDtoList.isEmpty() ? ResponseEntity.noContent()
                .build() : ResponseEntity.ok(clientDtoList);
    }

    @GetMapping(value ="/client/find/{uuid}")
    public ResponseEntity<ClientDto> getClientByUuid(@PathVariable String uuid) {
        log.info("endpoint request: find client by id");
        ClientDto clientDto = clientService.findById(uuid);
        return ResponseEntity.ok(clientDto);
    }

    @PutMapping(value ="/client/update/{uuid}")
    public ResponseEntity<ClientDto> updateClient(@PathVariable String uuid, @RequestBody ClientDto updatedClientDto) {
        log.info("endpoint request: update client");
        clientService.update(uuid, updatedClientDto);
        return ResponseEntity.ok(updatedClientDto);
    }

    @DeleteMapping(value ="/client/delete/{uuid}")
    public ResponseEntity<String> deleteClient(@PathVariable String uuid) {
        log.info("endpoint request: delete client");
        clientService.delete(uuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value ="/client/find-all/active-clients")
    public ResponseEntity<List<ClientDto>> findActiveClients() {
        log.info("endpoint request: find all active client");
        List<ClientDto> clientDtoList = clientService.findActiveClients();
        return clientDtoList.isEmpty() ? ResponseEntity.noContent()
                .build() : ResponseEntity.ok(clientDtoList);
    }
}

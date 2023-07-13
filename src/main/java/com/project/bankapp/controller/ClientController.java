package com.project.bankapp.controller;

import com.project.bankapp.entity.Client;
import com.project.bankapp.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bank")
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<String> createClient(@RequestBody Client client) {
        clientService.createClient(client);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Client>> getAllClients() {
        List<Client> clients = clientService.findAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/get/{uuid}")
    public ResponseEntity<Client> getClientById(@PathVariable UUID uuid) {
        Client client = clientService.findByUuid(uuid);
        return ResponseEntity.ok(client);
    }

    @PutMapping("/update/{uuid}")
    public ResponseEntity<String> updateClient(@PathVariable UUID uuid, @RequestBody Client updatedClient) {
        clientService.updateClient(updatedClient, uuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{uuid}")
    public ResponseEntity<String> deleteClient(@PathVariable UUID uuid) {
        clientService.deleteClient(uuid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}

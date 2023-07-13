package com.project.bankapp.controller;

import com.project.bankapp.entity.Client;
import com.project.bankapp.service.ClientService;
import lombok.RequiredArgsConstructor;
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
    public void createClient(@RequestBody Client client) {
        clientService.createClient(client);
    }

    @GetMapping("/getall")
    public List<Client> getAllClients() {
        return clientService.findAllClients();
    }

    @GetMapping("/get/{uuid}")
    public Client getClientById(@PathVariable UUID uuid) {
        return clientService.findByUuid(uuid);
    }

    @PutMapping("/update/{uuid}")
    public void updateClient(@PathVariable UUID uuid, @RequestBody Client updatedClient) {
        clientService.updateClient(updatedClient, uuid);
    }

    @DeleteMapping("/delete/{uuid}")
    public void deleteClient(@PathVariable UUID uuid) {
        clientService.deleteClient(uuid);
    }
}

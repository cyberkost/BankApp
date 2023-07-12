package com.project.bankapp.controller;

import com.project.bankapp.entity.Client;
import com.project.bankapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bank")
public class ClientController {
    @Autowired
    ClientRepository clientRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addClient(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String age,
            @RequestParam String citizenship,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String phone,
            @RequestParam String taxCode) {
        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setAge(age);
        client.setCitizenship(citizenship);
        client.setEmail(email);
        client.setAddress(address);
        client.setPhone(phone);
        client.setTaxCode(taxCode);
        clientRepository.save(client);
        return "saved";
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Client> findAllClients() {
        return clientRepository.findAll();
    }
}

package com.project.bankapp.controller;

import com.project.bankapp.entity.Client;
import com.project.bankapp.entity.enums.ClientStatus;
import com.project.bankapp.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        Iterable<Client> clients = clientRepository.findAll();
        model.put("clients", clients);
        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String firstName, @RequestParam String lastName,
                      @RequestParam String age, @RequestParam String citizenship,
                      @RequestParam String email, @RequestParam String address,
                      @RequestParam String phone, @RequestParam ClientStatus status,
                      Map<String, Object> model) {
        Client client = new Client(firstName, lastName, age, citizenship, email, address, phone, status);
        clientRepository.save(client);
        Iterable<Client> clients = clientRepository.findAll();
        model.put("clients", clients);
        return "main";
    }

//    @PostMapping("/filter")
//    public String filter(@RequestParam String filter, Map<String, Object> model) {
//        Iterable<Client> clients;
//        if (filter != null && filter.isEmpty()) {
//            clients = clientRepository.findClientByFirstName(filter);
//        } else {
//            clients = clientRepository.findAll();
//        }
//        model.put("clients", clients);
//        return "main";
//    }
}

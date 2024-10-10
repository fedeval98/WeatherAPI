package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.dtos.ClientDTO;
import com.opytha.weatherAPI.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping("/clients")
    public List<ClientDTO> getAllClient(){
        return clientService.getAllClientsDTO();
    }
}

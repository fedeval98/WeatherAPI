package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.dtos.ClientDTO;
import com.opytha.weatherAPI.dtos.jwt.CreateClient;
import com.opytha.weatherAPI.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ClientController {

    @Autowired
    private ClientService clientService;

    @RequestMapping("/clients")
    public List<ClientDTO> getAllClient(){
        return clientService.getAllClientsDTO();
    }

    @PostMapping("/register")
    public ResponseEntity<String> createNewClient (@RequestBody @Valid CreateClient createClient){
        clientService.createNewClient(createClient);
        return ResponseEntity.ok("Usuario Creador con exito");
    }
}

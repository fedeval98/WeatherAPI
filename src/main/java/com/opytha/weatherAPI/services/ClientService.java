package com.opytha.weatherAPI.services;

import com.opytha.weatherAPI.dtos.ClientDTO;
import com.opytha.weatherAPI.dtos.jwt.CreateClient;
import com.opytha.weatherAPI.models.Client;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<Client> getAllClients();

    List<ClientDTO> getAllClientsDTO();
    Client getAuthClient(String email);
    void saveClient(Client client);
    void deleteClient(String email);
    Client findByEmail(String email);
    void createNewClient(CreateClient createClient);
}

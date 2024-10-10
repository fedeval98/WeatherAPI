package com.opytha.weatherAPI.services;

import com.opytha.weatherAPI.dtos.ClientDTO;
import com.opytha.weatherAPI.models.Client;

import java.util.List;

public interface ClientService {
    List<Client> getAllClients();

    List<ClientDTO> getAllClientsDTO();
    Client getAuthClient(String email);
    void saveClient(Client client);
}

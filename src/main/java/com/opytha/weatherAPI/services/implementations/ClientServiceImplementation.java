package com.opytha.weatherAPI.services.implementations;

import com.opytha.weatherAPI.dtos.ClientDTO;
import com.opytha.weatherAPI.models.Client;
import com.opytha.weatherAPI.repositories.ClientRepository;
import com.opytha.weatherAPI.services.ClientService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplementation implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public List<ClientDTO> getAllClientsDTO() {
        return getAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    }

    @Override
    public Client getAuthClient (String email){
        return clientRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void saveClient(Client client) {
        clientRepository.save(client);
    }

    @Override
    public Client findByEmail(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client == null) {
            throw new EntityNotFoundException("El cliente con email " + email + " no existe.");
        }
        return client;
    }

    @Override
    public void deleteClient(String email) {
        Client client = clientRepository.findByEmail(email);
        if (client != null) {
            clientRepository.delete(client);
        } else {
            throw new EntityNotFoundException("El cliente con email " + email + " no existe.");
        }
    }

}

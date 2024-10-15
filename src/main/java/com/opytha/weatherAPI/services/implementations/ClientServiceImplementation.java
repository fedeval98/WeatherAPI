package com.opytha.weatherAPI.services.implementations;

import com.opytha.weatherAPI.dtos.ClientDTO;
import com.opytha.weatherAPI.dtos.jwt.CreateClient;
import com.opytha.weatherAPI.models.Client;
import com.opytha.weatherAPI.repositories.ClientRepository;
import com.opytha.weatherAPI.services.ClientService;
import com.opytha.weatherAPI.utils.exceptions.BadRequestException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImplementation implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    public void createNewClient(CreateClient createClient) {

        if(clientRepository.findByEmail(createClient.getEmail()) != null){
            throw new BadRequestException("El email ya se encuentra registrado");
        }

        Client client = new Client(createClient.getFirstName(), createClient.getLastName(), createClient.getEmail(), passwordEncoder.encode(createClient.getPassword()));

        saveClient(client);
    }

}

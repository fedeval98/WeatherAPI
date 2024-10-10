package com.opytha.weatherAPI;

import com.opytha.weatherAPI.models.Client;
import com.opytha.weatherAPI.repositories.ClientRepository;
import com.opytha.weatherAPI.services.implementations.ClientServiceImplementation;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImplementation clientServiceImplementation;

    @Test
    @Transactional
    public void find () throws Exception{
        Client testClient = new Client("John", "Doe", "John@Doe.com","1234");
        Mockito.when(clientRepository.findByEmail(testClient.getEmail()))
            .thenReturn(testClient);
        Client actual = clientServiceImplementation.findByEmail(testClient.getEmail());
        assertEquals(testClient, actual);
        System.out.println("Finalizo el test find");
    }

    @Test
    @Transactional
    public void delete () throws Exception{
        Client testClient = new Client("John", "Doe", "John@Doe.com","1234");
        Mockito.when(clientRepository.findByEmail(testClient.getEmail()))
                .thenReturn(testClient);
        clientServiceImplementation.deleteClient(testClient.getEmail());
        Mockito.verify(clientRepository).delete(testClient);
        System.out.println("Finalizo el test delete");
    }
}

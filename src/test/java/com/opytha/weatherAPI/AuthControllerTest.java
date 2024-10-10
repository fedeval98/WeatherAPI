package com.opytha.weatherAPI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opytha.weatherAPI.dtos.jwt.AuthRequest;
import com.opytha.weatherAPI.models.Client;
import com.opytha.weatherAPI.repositories.ClientRepository;
import com.opytha.weatherAPI.controllers.AuthController;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;



@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(){
        objectMapper = new ObjectMapper();
    }

    @Test
    @Transactional
    public void testLogin() throws Exception {
        // Objeto que recibe los datos de login
        AuthRequest loginRequest = new AuthRequest("fede@val.com", "fedeval");

        mockMvc.perform(post("/api/login")  // Cambia esta ruta por tu endpoint de login
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwt").exists());
    }

    @Test
    @Transactional
    public void testLoginInvalidCredentials() throws Exception {
        clientRepository.deleteAll();
        Client client = new Client("John", "Doe", "john.doe@example.com", passwordEncoder.encode("password123"));
        clientRepository.save(client);
        // Datos de login con credenciales incorrectas
        String jsonLogin = "{\"email\":\"john.doe@example.com\", \"password\":\"fedeval\"}";

        mockMvc.perform(post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonLogin))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.message").value("Credenciales incorrectas"));
    }
}

package com.opytha.weatherAPI.repositories;

import com.opytha.weatherAPI.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
}

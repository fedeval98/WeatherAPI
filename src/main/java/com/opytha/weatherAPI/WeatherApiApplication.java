package com.opytha.weatherAPI;

import com.opytha.weatherAPI.models.Client;
import com.opytha.weatherAPI.repositories.ClientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class WeatherApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherApiApplication.class, args);
	}

	@Autowired
	public PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository){
		return args -> {
			Client fede = new Client("Fede","Val","fede@val.com", passwordEncoder.encode("fedeval"));
			clientRepository.save(fede);
			System.out.println(fede);
		};
	}
}

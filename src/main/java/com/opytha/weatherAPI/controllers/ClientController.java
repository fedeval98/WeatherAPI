package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.dtos.ClientDTO;
import com.opytha.weatherAPI.dtos.jwt.CreateClient;
import com.opytha.weatherAPI.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Operation(
            summary = "Retrieve all available clients",
            description = "Retrieve all available clients. Only admins have access.",
            tags = {"ADMIN"}
    )
    @ApiResponses(value = {
            @ApiResponse(   responseCode = "200",
                            description = "List retrieved successfully" ,
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientDTO.class)
                            )),
            @ApiResponse(   responseCode = "400",
                            description = "Access denied: You or your account don't have admin permissions.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            ))
    })
    @GetMapping("/clients")
    public List<ClientDTO> getAllClient(){
        return clientService.getAllClientsDTO();
    }

    @Operation(
            summary = "Register a new client",
            description = "Allows users to register by providing their first name, last name, email, and password.",
            tags = {"ALL USERS"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Client registered successfully",
                    content = @Content(schema = @Schema(implementation = String.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error: Email is already in use",
                    content = @Content(schema = @Schema(implementation = String.class))
            )
    })
    @PostMapping("/register")
    public ResponseEntity<String> createNewClient (@RequestBody @Valid CreateClient createClient){
        clientService.createNewClient(createClient);
        return ResponseEntity.ok("Client created successfully.");
    }
}

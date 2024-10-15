package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.dtos.ClientDTO;
import com.opytha.weatherAPI.dtos.jwt.CreateClient;
import com.opytha.weatherAPI.services.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

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

    @Operation(
            summary = "Registrar un nuevo cliente",
            description = "Registra un nuevo cliente en el sistema proporcionando nombre, apellido, email y contraseña."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado satisfactoriamente",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Error de validación o email ya registrado",
                    content = @Content(schema = @Schema(implementation = String.class)))
    })
    @PostMapping("/register")
    public ResponseEntity<String> createNewClient (@RequestBody @Valid CreateClient createClient){
        clientService.createNewClient(createClient);
        return ResponseEntity.ok("Usuario Creador con exito");
    }
}

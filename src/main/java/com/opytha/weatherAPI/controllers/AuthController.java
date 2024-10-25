package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.configs.jwt.JwtService;
import com.opytha.weatherAPI.dtos.jwt.AuthRequest;
import com.opytha.weatherAPI.dtos.jwt.AuthResponse;
import com.opytha.weatherAPI.services.implementations.AuthServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private AuthServiceImplementation authServiceImplementation;

    @Operation(
            summary = "User login",
            description = "Authenticates the user using email and password, returning a JWT token on successful authentication.",
            tags = {"ALL USERS"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful. JWT token returned.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid credentials. Authentication failed.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)  // For the {"message": "Incorrect login credentials"} response.
                    )
            )
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            String jwt = authServiceImplementation.login(authRequest.getEmail(), authRequest.getPassword());
            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}

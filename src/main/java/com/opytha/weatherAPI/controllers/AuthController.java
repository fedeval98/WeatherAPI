package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.configs.jwt.JwtService;
import com.opytha.weatherAPI.dtos.jwt.AuthRequest;
import com.opytha.weatherAPI.dtos.jwt.AuthResponse;
import com.opytha.weatherAPI.services.implementations.AuthServiceImplementation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        try {
            // Obtener el JWT de la implementación del servicio de autenticación
            String jwt = authServiceImplementation.login(authRequest.getEmail(), authRequest.getPassword());

            // Crear la cookie con el JWT
            Cookie cookie = new Cookie("jwt", jwt);
            cookie.setHttpOnly(true);  // Asegura que no sea accesible via JavaScript
            cookie.setSecure(true);    // Asegura que solo se enviará a través de HTTPS
            cookie.setPath("/");       // Define el ámbito de la cookie (en este caso, todo el dominio)
            cookie.setMaxAge(60 * 60); // Duración de la cookie (1 hora)

            // Establecer SameSite en el encabezado "Set-Cookie"
            response.addHeader("Set-Cookie", "jwt=" + jwt + "; HttpOnly; Secure; Path=/; Max-Age=3600; SameSite=None");

            // Responder sin el JWT en el cuerpo, solo la cookie
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("message", e.getMessage()));
        }
    }
}

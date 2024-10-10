package com.opytha.weatherAPI.dtos;

import com.opytha.weatherAPI.models.Client;
import com.opytha.weatherAPI.models.QueryLog;
import com.opytha.weatherAPI.models.enums.Roles;

import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ClientDTO {
   private long id;

    private String firstName,lastName,email;
    private Roles rol;
    private List<QueryLogDTO> queryLog;

    public ClientDTO(Client client) {
        id = client.getId();
        firstName = client.getFirstName();
        lastName = client.getLastName();
        email = client.getEmail();
        rol = client.getRol();
        queryLog = client.getQueryLog().stream().map(QueryLogDTO::new).collect(Collectors.toList()).reversed();
    }
}

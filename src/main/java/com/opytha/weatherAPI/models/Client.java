package com.opytha.weatherAPI.models;

import com.opytha.weatherAPI.models.enums.Roles;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter (AccessLevel.NONE)
    private long id;

    private String firstName,lastName,email,password;

    @Enumerated(EnumType.STRING)
    @Setter(AccessLevel.NONE)
    private Roles rol = Roles.CLIENT;

    @Setter(AccessLevel.NONE)
    @OneToMany (mappedBy = "client", fetch = FetchType.EAGER)
    private List<QueryLog> queryLog = new ArrayList<>();

    public Client(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}

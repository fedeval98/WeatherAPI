package com.opytha.weatherAPI.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QueryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter (AccessLevel.NONE)
    private long id;

    @ManyToOne
    private Client client;

    private String query;

    private LocalDateTime timeStamp;

    public QueryLog(String query, LocalDateTime timeStamp) {
        this.query = query;
        this.timeStamp = timeStamp;
    }
}

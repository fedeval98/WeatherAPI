package com.opytha.weatherAPI.dtos;

import com.opytha.weatherAPI.models.QueryLog;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class QueryLogDTO {

    private long id;

    private String query;

    private LocalDateTime timeStamp;

    public QueryLogDTO (QueryLog queryLog) {
        id = queryLog.getId();
        query = queryLog.getQuery();
        timeStamp = queryLog.getTimeStamp();
    }
}

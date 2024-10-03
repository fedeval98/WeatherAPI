package com.opytha.weatherAPI.dtos.weather;

import lombok.Data;

@Data
public class Wind {
    private double speed;
    private long deg;
    private double gust;
}

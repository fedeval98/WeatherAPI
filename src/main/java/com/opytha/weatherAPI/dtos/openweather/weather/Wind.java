package com.opytha.weatherAPI.dtos.openweather.weather;

import lombok.Data;

@Data
public class Wind {
    private double speed;
    private long deg;
    private double gust;
}

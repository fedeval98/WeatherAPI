package com.opytha.weatherAPI.dtos.forecast;

import lombok.Data;

@Data
public class ForecastWind {
    private double speed;
    private long deg;
    private double gust;
}

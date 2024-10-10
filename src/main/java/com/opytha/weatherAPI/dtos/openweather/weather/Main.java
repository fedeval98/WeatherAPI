package com.opytha.weatherAPI.dtos.openweather.weather;

import lombok.Data;

@Data
public class Main {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private short pressure;
    private short sea_level;
    private short grnd_level;
    private short humidity;
}

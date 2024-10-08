package com.opytha.weatherAPI.dtos.openweather.weather;

import lombok.Data;

@Data
public class WeatherElement {
    private long id;
    private String main;
    private String description;
    private String icon;
}

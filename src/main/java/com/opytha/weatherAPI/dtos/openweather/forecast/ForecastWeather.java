package com.opytha.weatherAPI.dtos.openweather.forecast;


import lombok.Data;

@Data
public class ForecastWeather {
    private long id;
    private String main;
    private String description;
    private String icon;
}








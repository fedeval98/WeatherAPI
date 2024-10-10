package com.opytha.weatherAPI.dtos.openweather.weather;

import lombok.Data;

@Data
public class Sys {
    private long type;
    private long id;
    private String country;
    private long sunrise;
    private long sunset;
}

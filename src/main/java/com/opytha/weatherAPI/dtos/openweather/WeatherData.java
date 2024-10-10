package com.opytha.weatherAPI.dtos.openweather;

import com.opytha.weatherAPI.dtos.openweather.weather.*;
import lombok.*;

@Data
public class WeatherData {
    private long id;
    private String name;
    private int cod;
    private long timezone;
    private Coord coord;
    private WeatherElement[] weather;
    private String base;
    private Main main;
    private long visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
}

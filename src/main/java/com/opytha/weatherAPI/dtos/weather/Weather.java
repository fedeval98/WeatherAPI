package com.opytha.weatherAPI.dtos.weather;


import lombok.Data;

@Data
public class Weather {
    private Coord coord;
    private WeatherElement[] weather;
    private String base;
    private Main main;
    private long visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private long timezone;
    private long id;
    private String name;
    private long cod;
}








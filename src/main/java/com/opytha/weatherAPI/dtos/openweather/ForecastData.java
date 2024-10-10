package com.opytha.weatherAPI.dtos.openweather;

import com.opytha.weatherAPI.dtos.openweather.forecast.*;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ForecastData {
    private int cod;
    private int message;
    private int cnt;
    private List<forecast> list;
    private ForecastCity city;

    @Data
    public static class forecast{
        private long dt;
        private ForecastMain main;
        private ForecastWeather[] weather;
        private ForecastClouds clouds;
        private ForecastWind wind;
        private long visibility;
        private double pop;
        private Map<String, Double> rain; // mapeo para poder manejar el tipo rain.
        private ForecastSys sys;
        private String dt_txt;
    }
}

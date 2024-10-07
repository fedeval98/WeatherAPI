package com.opytha.weatherAPI.services;

import com.opytha.weatherAPI.dtos.ForecastData;
import com.opytha.weatherAPI.dtos.GeocodeData;
import com.opytha.weatherAPI.dtos.WeatherData;

import java.util.List;

public interface WeatherService {
    WeatherData getWeatherByCityName(String cityName);
    ForecastData getForecastByCityName(String cityName);
    List<GeocodeData> getGeolocationByCityName(String cityName);
}

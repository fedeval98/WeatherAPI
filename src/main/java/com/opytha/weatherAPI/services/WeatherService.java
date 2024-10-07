package com.opytha.weatherAPI.services;

import com.opytha.weatherAPI.dtos.ForecastData;
import com.opytha.weatherAPI.dtos.WeatherData;

public interface WeatherService {
    WeatherData getWeatherByCityName(String cityName);
    ForecastData getForecastByCityName(String cityName);
}

package com.opytha.weatherAPI.services;

import com.opytha.weatherAPI.dtos.WeatherData;

public interface WeatherService {
    WeatherData getWeatherByCityName(String cityName);
}

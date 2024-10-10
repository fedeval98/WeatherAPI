package com.opytha.weatherAPI.services;

import com.opytha.weatherAPI.dtos.openweather.AirPollutioNData;
import com.opytha.weatherAPI.dtos.openweather.ForecastData;
import com.opytha.weatherAPI.dtos.openweather.GeocodeData;
import com.opytha.weatherAPI.dtos.openweather.WeatherData;

import java.util.List;

public interface WeatherService {
    WeatherData getWeatherByCityName(String cityName);
    ForecastData getForecastByCityName(String cityName);
    List<GeocodeData> getGeolocationByCityName(String cityName);
    AirPollutioNData getPollutionByCityName(String cityName);
}

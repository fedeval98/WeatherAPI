package com.opytha.weatherAPI.services;

import com.opytha.weatherAPI.dtos.openweather.AirPollutioNData;
import com.opytha.weatherAPI.dtos.openweather.ForecastData;
import com.opytha.weatherAPI.dtos.openweather.GeocodeData;
import com.opytha.weatherAPI.dtos.openweather.WeatherData;
import com.opytha.weatherAPI.models.Client;

import java.util.List;

public interface WeatherService {
    WeatherData getWeatherByCityName(String cityName, String email, String lang, String unit);
    ForecastData getForecastByCityName(String cityName, String email, String lang, String unit);
    List<GeocodeData> getGeolocationByCityName(String cityName, String email, String lang, String unit);
    AirPollutioNData getPollutionByCityName(String cityName, String email, String lang, String unit);
}

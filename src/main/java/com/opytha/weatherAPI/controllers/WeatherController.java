package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.dtos.ForecastData;
import com.opytha.weatherAPI.dtos.WeatherData;
import com.opytha.weatherAPI.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/{cityName}")
    public ResponseEntity<WeatherData> getWeatherByCityName(@PathVariable String cityName) {
        WeatherData weatherData = weatherService.getWeatherByCityName(cityName);
        return ResponseEntity.ok(weatherData);
    }

    @GetMapping("/forecast/{cityName}")
    public ResponseEntity<ForecastData> getWeather5daysByCityName(@PathVariable String cityName) {
        ForecastData forecastData = weatherService.getForecastByCityName(cityName);
        return ResponseEntity.ok(forecastData);
    }

}

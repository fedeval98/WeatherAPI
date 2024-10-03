package com.opytha.weatherAPI.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opytha.weatherAPI.dtos.WeatherData;
import com.opytha.weatherAPI.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherServiceImplementation implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${API_KEY}")
    private String ApiKey;

    @Override
    public WeatherData getWeatherByCityName(String cityName) {
        // Construyo la URL de la API de OpenWeatherMap con el nombre de la ciudad
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&units=metric" + "&lang=es" + "&appid="+ApiKey;

        // Realiza la llamada a la API y manejar la respuesta conviertiendolo en un JSON
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Convier la respuesta JSON a un objeto WeatherData
        ObjectMapper objectMapper = new ObjectMapper();
        WeatherData weatherData = null;
        try {
            weatherData = objectMapper.readValue(response.getBody(), WeatherData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return weatherData;
    }
}

package com.opytha.weatherAPI.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opytha.weatherAPI.dtos.openweather.AirPollutioNData;
import com.opytha.weatherAPI.dtos.openweather.ForecastData;
import com.opytha.weatherAPI.dtos.openweather.GeocodeData;
import com.opytha.weatherAPI.dtos.openweather.WeatherData;
import com.opytha.weatherAPI.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class WeatherServiceImplementation implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${API_KEY}")
    private String ApiKey;

    @Override
    @Cacheable(value = "weatherCache", key = "#cityName")
    public WeatherData getWeatherByCityName(String cityName) {
        // Construyo la URL de la API de OpenWeatherMap con el nombre de la ciudad
        String url =    "https://api.openweathermap.org/data/2.5/weather?q=" + cityName +
                        "&units=metric" +
                        "&lang=es" +
                        "&appid="+ApiKey;

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

    @Override
    @Cacheable(value = "forecastCache", key = "#cityName")
    public ForecastData getForecastByCityName(String cityName) {
        // Construyo la URL de la API de OpenWeatherMap con el nombre de la ciudad
        String url =    "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName +
                        "&units=metric" +
                        "&lang=es" +
                        "&appid="+ApiKey;

        // Realiza la llamada a la API y manejar la respuesta conviertiendolo en un JSON
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Convier la respuesta JSON a un objeto WeatherData
        ObjectMapper objectMapper = new ObjectMapper();
        ForecastData forecastData = null;
        try {
            forecastData = objectMapper.readValue(response.getBody(), ForecastData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return forecastData;
    }

    @Override
    @Cacheable(value = "geolocationCache", key = "#cityName")
    public List<GeocodeData> getGeolocationByCityName(String cityName) {

        String url =    "https://api.openweathermap.org/geo/1.0/direct?q=" + cityName +
                        "&limit=5" +
                        "&units=metric" +
                        "&lang=es" +
                        "&appid="+ApiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        List<GeocodeData> geolocationData = null;
        try {
            geolocationData = objectMapper.readValue(response.getBody(),
                                objectMapper.getTypeFactory().constructCollectionType(List.class, GeocodeData.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return geolocationData;
    }

    @Override
    @Cacheable(value = "airPollutionCache", key = "#cityName")
    public AirPollutioNData getPollutionByCityName(String cityName) {
        List<GeocodeData> geolocationData = getGeolocationByCityName(cityName);

        if (geolocationData == null || geolocationData.isEmpty()) {
            throw new RuntimeException("No se encontró geolocalización para la ciudad: " + cityName);
        }

        double lat = geolocationData.get(0).getLat();
        double lon = geolocationData.get(0).getLon();

        String url =    "https://api.openweathermap.org/data/2.5/air_pollution?lat="+lat +
                        "&lon="+ lon +
                        "&units=metric" +
                        "&lang=es" +
                        "&appid="+ApiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        AirPollutioNData pollutioNData = null;
        try {
            pollutioNData = objectMapper.readValue(response.getBody(), AirPollutioNData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return pollutioNData;
    }
}

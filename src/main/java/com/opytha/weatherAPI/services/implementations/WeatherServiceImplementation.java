package com.opytha.weatherAPI.services.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opytha.weatherAPI.dtos.openweather.AirPollutioNData;
import com.opytha.weatherAPI.dtos.openweather.ForecastData;
import com.opytha.weatherAPI.dtos.openweather.GeocodeData;
import com.opytha.weatherAPI.dtos.openweather.WeatherData;
import com.opytha.weatherAPI.models.Client;
import com.opytha.weatherAPI.models.QueryLog;
import com.opytha.weatherAPI.services.ClientService;
import com.opytha.weatherAPI.services.WeatherService;
import com.opytha.weatherAPI.utils.exceptions.BadRequestException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeatherServiceImplementation implements WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ClientService clientService;

    @Value("${API_KEY}")
    private String ApiKey;

    @Override
    @Cacheable(value = "weatherCache", key = "#cityName + '_' + #lang + '_' + #unit")
    @Transactional
    public WeatherData getWeatherByCityName(String cityName, String email, String lang, String unit) {

        Client clientAuth = clientService.getAuthClient(email);

        if(clientAuth == null){
            throw new BadRequestException("User not signed up.");
        }

        // Construyo la URL de la API de OpenWeatherMap con el nombre de la ciudad
        String url =    "https://api.openweathermap.org/data/2.5/weather?q=" + cityName +
                        "&units="+ unit +
                        "&lang="+ lang +
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

        QueryLog query = new QueryLog("Current Wather on: "+cityName, LocalDateTime.now());

        clientAuth.addQueryLog(query);

        clientService.saveClient(clientAuth);

        return weatherData;
    }

    @Override
    @Cacheable(value = "weatherCache", key = "#cityName + '_' + #lang + '_' + #unit")
    @Transactional
    public ForecastData getForecastByCityName(String cityName, String email, String lang, String unit) {

        Client clientAuth = clientService.getAuthClient(email);

        if(clientAuth == null){
            throw new BadRequestException("User not signed up.");
        }

        // Construyo la URL de la API de OpenWeatherMap con el nombre de la ciudad
        String url =    "https://api.openweathermap.org/data/2.5/forecast?q=" + cityName +
                        "&units="+ unit +
                        "&lang="+ lang +
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

        QueryLog query = new QueryLog("Forecast for: "+cityName, LocalDateTime.now());

        clientAuth.addQueryLog(query);

        clientService.saveClient(clientAuth);

        return forecastData;
    }

    @Override
    @Cacheable(value = "weatherCache", key = "#cityName + '_' + #lang + '_' + #unit")
    @Transactional
    public List<GeocodeData> getGeolocationByCityName(String cityName, String email, String lang, String unit) {

        Client clientAuth = clientService.getAuthClient(email);

        if(clientAuth == null){
            throw new BadRequestException("User not signed up.");
        }

        String url =    "https://api.openweathermap.org/geo/1.0/direct?q=" + cityName +
                        "&limit=5" +
                        "&units="+ unit +
                        "&lang="+ lang +
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

        QueryLog query = new QueryLog("Geolocation of: "+cityName, LocalDateTime.now());

        clientAuth.addQueryLog(query);

        clientService.saveClient(clientAuth);

        return geolocationData;
    }

    @Override
    @Cacheable(value = "weatherCache", key = "#cityName + '_' + #lang + '_' + #unit")
    @Transactional
    public AirPollutioNData getPollutionByCityName(String cityName, String email, String lang, String unit) {

        Client clientAuth = clientService.getAuthClient(email);

        if(clientAuth == null){
            throw new BadRequestException("User not signed up.");
        }

        List<GeocodeData> geolocationData = getGeolocationByCityName(cityName, email,lang,unit);

        if (geolocationData == null || geolocationData.isEmpty()) {
            throw new BadRequestException("Can not found geolocation for: " + cityName);
        }

        double lat = geolocationData.getFirst().getLat();
        double lon = geolocationData.getFirst().getLon();

        String url =    "https://api.openweathermap.org/data/2.5/air_pollution?lat="+lat +
                        "&lon="+ lon +
                        "&units="+ unit +
                        "&lang="+ lang +
                        "&appid="+ApiKey;

        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        AirPollutioNData pollutioNData = null;
        try {
            pollutioNData = objectMapper.readValue(response.getBody(), AirPollutioNData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        QueryLog query = new QueryLog("Air pollution for: "+cityName, LocalDateTime.now());

        clientAuth.addQueryLog(query);

        clientService.saveClient(clientAuth);

        return pollutioNData;
    }

}
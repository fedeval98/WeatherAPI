package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.dtos.AirPollutioNData;
import com.opytha.weatherAPI.dtos.ForecastData;
import com.opytha.weatherAPI.dtos.GeocodeData;
import com.opytha.weatherAPI.dtos.WeatherData;
import com.opytha.weatherAPI.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.opytha.weatherAPI.utils.eTagGenerator.generateETag;

@RequestMapping("/api")
@RestController
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather/{cityName}")
    public ResponseEntity<WeatherData> getWeatherByCityName(@PathVariable String cityName,
                                                            @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false)String ifNoneMatch){
        WeatherData weatherData = weatherService.getWeatherByCityName(cityName);

        String eTag = generateETag(weatherData);
        if(ifNoneMatch != null && ifNoneMatch.equals(eTag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag(eTag).body(weatherData);
    }

    @GetMapping("/forecast/{cityName}")
    public ResponseEntity<ForecastData> getWeather5daysByCityName(@PathVariable String cityName,
                                                                  @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false)String ifNoneMatch) {
        ForecastData forecastData = weatherService.getForecastByCityName(cityName);

        String eTag = generateETag(forecastData);
        if(ifNoneMatch != null && ifNoneMatch.equals(eTag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag(eTag).body(forecastData);
    }

    @GetMapping("/geo/{cityName}")
    public ResponseEntity<List<GeocodeData>> getGeolocationByCityName(@PathVariable String cityName,
                                                                      @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false)String ifNoneMatch) {
        List<GeocodeData> geolocationData = weatherService.getGeolocationByCityName(cityName);

        String eTag = generateETag(geolocationData);
        if(ifNoneMatch != null && ifNoneMatch.equals(eTag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag(eTag).body(geolocationData);
    }

    @GetMapping("/pollution/{cityName}")
    public ResponseEntity<AirPollutioNData> getPollutionByCityName(@PathVariable String cityName,
                                                                      @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false)String ifNoneMatch) {
        AirPollutioNData airPollutioNData = weatherService.getPollutionByCityName(cityName);

        String eTag = generateETag(airPollutioNData);
        if(ifNoneMatch != null && ifNoneMatch.equals(eTag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag(eTag).body(airPollutioNData);
    }

}
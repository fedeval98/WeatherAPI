package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.dtos.openweather.AirPollutioNData;
import com.opytha.weatherAPI.dtos.openweather.ForecastData;
import com.opytha.weatherAPI.dtos.openweather.GeocodeData;
import com.opytha.weatherAPI.dtos.openweather.WeatherData;
import com.opytha.weatherAPI.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Obtiene el clima actual de una ciudad o lugar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La información del clima se obtuvo correctamente.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherData.class))
                    ),
                    @ApiResponse(ref = "#/components/responses/NotModifiedResponse"),
                    @ApiResponse(ref = "#/components/responses/TooManyRequestsResponse")
            }
    )
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
    @Operation(
            summary = "Obtiene el pronostico del clima a 5 dias de una ciudad o lugar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "El pronóstico del clima para los próximos 5 días se obtuvo correctamente.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ForecastData.class))
                    ),
                    @ApiResponse(ref = "#/components/responses/NotModifiedResponse"),
                    @ApiResponse(ref = "#/components/responses/TooManyRequestsResponse")
            }
    )
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
    @Operation(
            summary = "Obtiene la geolocalizacion de una ciudad o lugar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La información de la geolocalizacion se obtuvo correctamente.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GeocodeData.class))
                    ),
                    @ApiResponse(ref = "#/components/responses/NotModifiedResponse"),
                    @ApiResponse(ref = "#/components/responses/TooManyRequestsResponse")
            }
    )
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
    @Operation(
            summary = "Obtiene contaminacion del aire de una ciudad o lugar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "La información de la contaminacion del aire se obtuvo correctamente.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = AirPollutioNData.class))
                    ),
                    @ApiResponse(ref = "#/components/responses/NotModifiedResponse"),
                    @ApiResponse(ref = "#/components/responses/TooManyRequestsResponse")
            }
    )
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
package com.opytha.weatherAPI.controllers;

import com.opytha.weatherAPI.configs.jwt.JwtService;
import com.opytha.weatherAPI.dtos.UnitLangDTO;
import com.opytha.weatherAPI.dtos.openweather.AirPollutioNData;
import com.opytha.weatherAPI.dtos.openweather.ForecastData;
import com.opytha.weatherAPI.dtos.openweather.GeocodeData;
import com.opytha.weatherAPI.dtos.openweather.WeatherData;
import com.opytha.weatherAPI.services.ValidationService;
import com.opytha.weatherAPI.services.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.opytha.weatherAPI.utils.eTagGenerator.generateETag;

@RestController
@RequestMapping("/api")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ValidationService validationService;

    @Operation(
            summary = "Get current weather by city or location",
            description = "Returns the current weather data for the specified city or location.",
            tags = {"CLIENT"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Weather data retrieved successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = WeatherData.class)
                    )
            ),
            @ApiResponse(ref = "#/components/responses/NotModifiedResponse"),
            @ApiResponse(ref = "#/components/responses/TooManyRequestsResponse")
    })
    @GetMapping("/weather/{cityName}")
    public ResponseEntity<WeatherData> getWeatherByCityName(@PathVariable String cityName,
                                                            @RequestBody @Valid UnitLangDTO unitLangDTO,
                                                            @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false)String ifNoneMatch,
                                                            HttpServletRequest request){

        // Obtener JWT desde las cookies
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Obtener usuario desde el token
        String username = jwtService.extractUserName(token);
        if (username == null || jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Validations
        String lang = unitLangDTO.getLang();
        String unit = unitLangDTO.getUnit();

        validationService.isValidLang(lang);
        validationService.isValidUnit(unit);

        WeatherData weatherData = weatherService.getWeatherByCityName(cityName,username,lang,unit);

        String eTag = generateETag(weatherData);
        if(ifNoneMatch != null && ifNoneMatch.equals(eTag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag(eTag).body(weatherData);
    }

    @Operation(
            summary = "Get 5-day weather forecast",
            description = "Returns a 5-day weather forecast for the specified city or location.",
            tags = {"CLIENT"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Forecast data retrieved successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ForecastData.class)
                    )
            ),
            @ApiResponse(ref = "#/components/responses/NotModifiedResponse"),
            @ApiResponse(ref = "#/components/responses/TooManyRequestsResponse")
    })
    @GetMapping("/forecast/{cityName}")
    public ResponseEntity<ForecastData> getWeather5daysByCityName(@PathVariable String cityName,
                                                                  @RequestBody @Valid UnitLangDTO unitLangDTO,
                                                                  @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false)String ifNoneMatch,
                                                                  HttpServletRequest request) {

        // Obtener JWT desde las cookies
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Obtener usuario desde el token
        String username = jwtService.extractUserName(token);
        if (username == null || jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }


        // Validations
        String lang = unitLangDTO.getLang();
        String unit = unitLangDTO.getUnit();

        validationService.isValidLang(lang);
        validationService.isValidUnit(unit);

        ForecastData forecastData = weatherService.getForecastByCityName(cityName,username,lang,unit);

        String eTag = generateETag(forecastData);
        if(ifNoneMatch != null && ifNoneMatch.equals(eTag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag(eTag).body(forecastData);
    }

    @Operation(
            summary = "Get geolocation of a city or location",
            description = "Returns the geolocation data for the specified city or location.",
            tags = {"CLIENT"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Geolocation data retrieved successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = GeocodeData.class)
                    )
            ),
            @ApiResponse(ref = "#/components/responses/NotModifiedResponse"),
            @ApiResponse(ref = "#/components/responses/TooManyRequestsResponse")
    })
    @GetMapping("/geo/{cityName}")
    public ResponseEntity<List<GeocodeData>> getGeolocationByCityName(@PathVariable String cityName,
                                                                      @RequestBody @Valid UnitLangDTO unitLangDTO,
                                                                      @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false)String ifNoneMatch,
                                                                      HttpServletRequest request) {

        // Obtener JWT desde las cookies
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Obtener usuario desde el token
        String username = jwtService.extractUserName(token);
        if (username == null || jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Validations
        String lang = unitLangDTO.getLang();
        String unit = unitLangDTO.getUnit();

        validationService.isValidLang(lang);
        validationService.isValidUnit(unit);

        List<GeocodeData> geolocationData = weatherService.getGeolocationByCityName(cityName,username,lang,unit);

        String eTag = generateETag(geolocationData);
        if(ifNoneMatch != null && ifNoneMatch.equals(eTag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag(eTag).body(geolocationData);
    }
    @Operation(
            summary = "Get air pollution data of a city or location",
            description = "Returns air pollution data for the specified city or location.",
            tags = {"CLIENT"}
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Air pollution data retrieved successfully.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AirPollutioNData.class)
                    )
            ),
            @ApiResponse(ref = "#/components/responses/NotModifiedResponse"),
            @ApiResponse(ref = "#/components/responses/TooManyRequestsResponse")
    })
    @GetMapping("/pollution/{cityName}")
    public ResponseEntity<AirPollutioNData> getPollutionByCityName(@PathVariable String cityName,
                                                                   @RequestBody @Valid UnitLangDTO unitLangDTO,
                                                                   @RequestHeader(value = HttpHeaders.IF_NONE_MATCH, required = false)String ifNoneMatch,
                                                                   HttpServletRequest request) {

        // Obtener JWT desde las cookies
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Obtener usuario desde el token
        String username = jwtService.extractUserName(token);
        if (username == null || jwtService.isTokenExpired(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Validations
        String lang = unitLangDTO.getLang();
        String unit = unitLangDTO.getUnit();

        validationService.isValidLang(lang);
        validationService.isValidUnit(unit);

        AirPollutioNData airPollutioNData = weatherService.getPollutionByCityName(cityName,username,lang,unit);

        String eTag = generateETag(airPollutioNData);
        if(ifNoneMatch != null && ifNoneMatch.equals(eTag)){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).eTag(eTag).build();
        }

        return ResponseEntity.ok().cacheControl(CacheControl.noCache()).eTag(eTag).body(airPollutioNData);
    }

}
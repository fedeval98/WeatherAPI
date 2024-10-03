package com.opytha.weatherAPI.dtos.weather;

import lombok.Data;

import java.util.Map;

@Data
public class Geocode {
    private String name;
    private Map<String, String> localNames;
    private double lat;
    private double lon;
    private String country;
    private String state;

}

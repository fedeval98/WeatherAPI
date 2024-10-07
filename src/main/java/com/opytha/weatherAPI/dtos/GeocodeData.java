package com.opytha.weatherAPI.dtos;

import lombok.Data;

import java.util.Map;

@Data
public class GeocodeData {
    private String name;
    private Map<String, String> local_names;
    private double lat;
    private double lon;
    private String country;
    private String state;
}
